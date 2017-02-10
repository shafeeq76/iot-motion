import RPi.GPIO as GPIO
import time
import threading
import requests
import json

API_DOMAIN = 'https://mighty-dawn-90967.herokuapp.com'

class Room (threading.Thread):

  def __init__(self, room_id, room_name, room_output_pin, green, red):
    threading.Thread.__init__(self)
    #Initializing the Room Details
    self.is_occupied = 0
    self.room_output_pin = room_output_pin
    self.room_name = room_name
    self.room_id = room_id
    self.green = green
    self.red = red

    #Settting up the GPIO board and pins
    GPIO.setmode(GPIO.BOARD)

    #Setting up Motion sensor output as input
    GPIO.setup(self.room_output_pin, GPIO.IN)

    #setting up the Status LEDs
    GPIO.setup(self.green, GPIO.OUT)
    GPIO.setup(self.red, GPIO.OUT)

  def run(self):
    check_room(self)

def check_room(room):
  headers = {'Content-Type': 'application/json'}
  curr_status=prev_status=0

  while True:
    curr_status=GPIO.input(room.room_output_pin)

    if curr_status==0 and curr_status!=prev_status: #When output from motion sensor is LOW
      print room.room_name, "Available", '@', time.ctime()
      data = {'id': room.room_id, 'status': curr_status}
      GPIO.output(room.green, 0) #Turn OFF LED
      GPIO.output(room.red, 1)
      requests.put(API_DOMAIN + '/v1/rooms/update_status', headers=headers, params=data)

    elif curr_status==1 and curr_status!=prev_status: #When output from motion sensor is HIGH
      print room.room_name, "Occupied", '@', time.ctime()
      data = {'id': room.room_id, 'status': curr_status}     
      GPIO.output(room.green, 1) #Turn ON LED
      GPIO.output(room.red, 0)
      requests.put(API_DOMAIN + '/v1/rooms/update_status', headers=headers, params=data)
    time.sleep(3)
    prev_status = curr_status

  GPIO.cleanup()

#Creating instances for Room object and checking the status parallel.
response = requests.get(API_DOMAIN + '/v1/rooms')
rooms = json.loads(response.content.decode('utf-8'))

room1 = Room(1, rooms[0]['name'], 11,8,7)
room2 = Room(2, rooms[1]['name'], 15, 35, 36)
room3 = Room(3, rooms[2]['name'], 13, 37, 38)
room1.start()
room2.start()
room3.start()
