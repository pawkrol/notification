#define START_LED 2
#define END_LED 6
#define BUTTON 7

int readVal = 0;
int mode = 0;
boolean blinking = false;
unsigned long previousMillis = 0;

void test() {
  unsigned long currentMillis = millis();

  if (currentMillis - previousMillis >= 500) {
    previousMillis = currentMillis;

    blinking = false;

    Serial.println(1);
  }
}

void blinkModeBounce(){
  for (int i = START_LED; i <= END_LED; i++) {
    digitalWrite(i, HIGH);
    delay(100);
    digitalWrite(i, LOW);
  }

  for (int i = END_LED; i >= START_LED; i--) {
    digitalWrite(i, HIGH);
    delay(100);
    digitalWrite(i, LOW);
  }
}

void blinkModeTrain(){
  for (int i = 0; i <= (END_LED - START_LED) + 2; i++) {
    if (i <= (END_LED - START_LED)) {
      digitalWrite(START_LED + i, HIGH);
    }

    if (i >= 2){
      digitalWrite(START_LED + i - 2, LOW);  
    }
    
    delay(100);
  }
}

void blinkLeds() {
  switch (mode) {
    case 0:
      blinkModeBounce();
      break;

    case 1:
      blinkModeTrain();
      break;
  }
}

void readSerial() {
  if (Serial.available()) {
    readVal = Serial.read();

    switch (readVal) {
      case 1:
        blinking = true;
        break;

      case 2:
        blinking = false;
        for (int i = END_LED; i >= START_LED; i--) {
          digitalWrite(i, LOW);
        }
        break;

      case 10:
        mode = 0;
        break;

      case 11:
        mode = 1;
        break;
    }
  }
}

void setup() {
  Serial.begin(9600);

  for (int i = START_LED; i <= END_LED; i++) {
    pinMode(i, OUTPUT);
  }

  pinMode(BUTTON, INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(BUTTON), test, FALLING);
}

void loop() {
  readSerial();

  if (blinking) {
    blinkLeds();
  }
}
