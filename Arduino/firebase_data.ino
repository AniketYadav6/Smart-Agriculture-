 #include <ESP8266WiFi.h>                 
#include <FirebaseArduino.h>      
#include <DHT.h>              
 
#define FIREBASE_HOST "nodemcufirebase-142b3-default-rtdb.firebaseio.com"      
#define FIREBASE_AUTH "tKFBQNEWoqybMgKXH0hPXqL08cdlCrjOMCBwfvqj"            
#define WIFI_SSID "hello"                                  
#define WIFI_PASSWORD "123456789"            
 
#define DHTPIN 5                                            // Digital pin connected to DHT11
#define DHTTYPE DHT11                                        // Initialize dht type as DHT 11
DHT dht(DHTPIN, DHTTYPE);   
int moistureSensor = A0;     
int pin=14;  
                                      
 
void setup() 
{
  Serial.begin(9600);
  dht.begin();                                                  //reads dht sensor data 
               
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                  
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
 
  Serial.println();
  Serial.print("Connected");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());                               //prints local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                 // connect to the firebase
  pinMode(moistureSensor,INPUT);
  pinMode(pin,OUTPUT);
 
}
 
void loop() 
{
  float h = dht.readHumidity();                                 // Read Humidity
  float t = dht.readTemperature();                              // Read temperature
  int moi = analogRead(moistureSensor);
  int status=Firebase.getInt("nodemcufirebase-142b3-default-rtdb");  

  
  if (isnan(h) || isnan(t))                                     // Checking sensor working
  {                                   
    Serial.println("Failed to read from DHT sensor!");
    return;
  } 
  Serial.print("Humidity: ");  
  Serial.print(h);
  String fireHumid = String(h) + String("%");                   //Humidity integer to string conversion
  
  Serial.print("%  Temperature: ");  
  Serial.print(t);  
  Serial.println("°C ");
  String fireTemp = String(t) + String("°C");                  //Temperature integer to string conversion
  Serial.print("Moisture:");
  Serial.println(moi);
   if(status==1){
      Serial.println("done");
      digitalWrite(pin,LOW);
   }
   else{
    Serial.println("cancel");
      digitalWrite(pin,HIGH);
   } 
 
  Firebase.setInt("Humidity",h);            //setup path to send Humidity readings
  Firebase.setInt("Temperature",t);         //setup path to send Temperature readings
   Firebase.setInt("Moisture",moi);
     
    if (Firebase.failed()) 
    {
 
      Serial.print("pushing /logs failed:");
      Serial.println(Firebase.error()); 
      return;
  }
}
