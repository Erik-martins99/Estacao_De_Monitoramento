 #include <WiFi.h>
#include <HTTPClient.h>
#include <time.h>
#include <DHT.h>

// Definições para o calculo da tempertura
#define ADC_RESOLUTION 4095.0 // Resolução do ADC (0-4095 para um ADC de 12 bits)
#define V_REF 3.3             // Tensão de referência do ADC (em volts)
#define R_FIXED 10000.0       // Valor do resistor fixo (em ohms)
#define BETA 3950.0           // Valor Beta do NTC (em Kelvin)
#define T0 298.15             // Temperatura de referência em Kelvin (25°C)
#define R0 10000.0            // Resistência do NTC a 25°C (10kΩ)

// Pinos analogicos
const int pinLuz = 32;  
const int pinTemp = 35;
const int pinUmid = 33;
const int pinSound = 15;

const char *ssid = "M201";
const char *password = "apartamento201";

//URL da API
String apiUrl = "http://192.168.0.223:8080/condicaoClimatica";

// Defininco configurações para o DHT11
DHT dht(pinUmid, DHT11);

void setup() {
  dht.begin();
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  pinMode(2, OUTPUT);  // set the LED pin mode

  delay(2000);

  // We start by connecting to a WiFi network

  Serial.print("Connecting to ");
  Serial.println(ssid);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void ST_conection() {
  if(WiFi.status() == WL_CONNECTED){
    digitalWrite(2, HIGH);
    
  } else {
    digitalWrite(2, LOW);
    Serial.println("Erro: Wi-Fi desconectado");
  }
}

void get_api() {
  if (WiFi.status() == WL_CONNECTED) { // Verifica se está conectado antes de fazer a requisição
    HTTPClient http;
    http.begin(apiUrl);
    
    // Envia a requisição GET
    int httpCode = http.GET();
    
    // Verifica o status da resposta
    if (httpCode > 0) {
      if (httpCode == HTTP_CODE_OK) {
        String payload = http.getString();
        Serial.println("Resposta da API:");
        Serial.println(payload);
      }
    } else {
      Serial.printf("Erro ao realizar a requisição: %s\n", http.errorToString(httpCode).c_str());
    }
    http.end();
  } else {
    Serial.println("Wi-Fi desconectado, não foi possível fazer a requisição GET");
  }
}

void post_api(double temperatura, int luminosidade, double umidade, int som) {
  if (WiFi.status() == WL_CONNECTED) { 
    HTTPClient http;
    http.begin(apiUrl); // Inicia a conexão com a URL

    // Define o tipo de conteúdo (JSON)
    http.addHeader("Content-Type", "application/json");

    // Dados a serem enviados no corpo da requisição
    String postData = "{\"temperatura\":" + String(temperatura, 2) + ", \"luminosidade\":" + String(luminosidade) + ", \"umidade\":" + String(umidade) + + ", \"som\":" + String(som) + "}";

    int httpCode = http.POST(postData);

    if (httpCode > 0) {
      String payload = http.getString();
      Serial.println("Resposta da API:");
      Serial.println(payload);
    } else {
      Serial.printf("Erro ao realizar a requisição: %s\n", http.errorToString(httpCode).c_str());
    }
    http.end(); // Fecha a conexão HTTP
  } else {
    Serial.println("Wi-Fi desconectado, não foi possível fazer a requisição POST");
  }
}

double getTemperatureCelsius(int adcValue) {
  double Vout = (adcValue / ADC_RESOLUTION) * V_REF;
  double R_ntc = R_FIXED * (V_REF / Vout - 1);
  double tempK = 1 / (1 / T0 + (1 / BETA) * log(R_ntc / R0));
  double tempC = tempK - 273.15;
  
  return tempC;
}

int getPercentLight(int ldrValue) {
  int percentLight = (ldrValue * 100) / ADC_RESOLUTION;
  return percentLight;
}

double getPercentHumidity() {
  float humidity = 60 + dht.readHumidity();

  if (isnan(humidity)) {
    Serial.println("Falha ao ler o sensor DHT!");
    return 0;
  } else {
    return humidity;
  }
}

int getSound() {
  if (digitalRead(pinSound) == HIGH) {
    return 1;
  } 
  return 0;
}

void loop() {
  
  // Recebendo o valor analogicos dos sensores
  int luzValue = analogRead(pinLuz);
  int tempValue = analogRead(pinTemp);

  //Verifica a conexão WIFI
  ST_conection();

  // Post
  post_api(getTemperatureCelsius(tempValue), getPercentLight(luzValue), getPercentHumidity(), getSound());

  // GET (findAll da API)
  get_api();

  delay(600000);  // Atraso de 10 segundos (10000 ms)
}
