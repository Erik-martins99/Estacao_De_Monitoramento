import requests
import numpy as np
import pandas as pd
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense
from sklearn.preprocessing import MinMaxScaler
import matplotlib.pyplot as plt
import datetime

# Função para buscar dados da API
def get_data_from_api(url):
    response = requests.get(url)
    response.raise_for_status()  # Verifica se houve erro na requisição
    return response.json()

# URL da API
url = "http://localhost:8080/condicaoClimatica"
dados = get_data_from_api(url)

# Conversão dos dados para DataFrame
df = pd.DataFrame(dados)
df['data'] = pd.to_datetime(df['data'])
df.set_index('data', inplace=True)

# Selecionando e normalizando as variáveis
scaler = MinMaxScaler()
scaled_data = scaler.fit_transform(df[['temperatura', 'luminosidade', 'umidade', 'indiceDeCalor', 'som', 'gas']])

# Preparação dos dados para LSTM
def create_sequences(data, seq_length=10):
    X, y = [], []
    for i in range(len(data) - seq_length):
        X.append(data[i:i + seq_length])
        y.append(data[i + seq_length])
    return np.array(X), np.array(y)

seq_length = 5  # Número de passos anteriores para prever
X, y = create_sequences(scaled_data, seq_length)

# Construção do modelo LSTM
model = Sequential([
    LSTM(50, activation='relu', input_shape=(X.shape[1], X.shape[2])),
    Dense(25, activation='relu'),
    Dense(y.shape[1])  # Número de variáveis de saída
])
model.compile(optimizer='adam', loss='mse')

# Treinamento do modelo
model.fit(X, y, epochs=20, batch_size=16, validation_split=0.2)

# Função para fazer a previsão para 1 hora à frente
def forecast(model, data, steps=6):  # Ex.: 6 passos de 10 minutos para 1 hora
    prediction = []
    current_sequence = data[-seq_length:]
    for _ in range(steps):
        pred = model.predict(current_sequence[np.newaxis, :, :])[0]
        prediction.append(pred)
        current_sequence = np.vstack([current_sequence[1:], pred])
    return scaler.inverse_transform(prediction)

# Prevendo as variáveis para a próxima hora
forecast_1h = forecast(model, scaled_data, steps=6)

# Geração do gráfico de previsão para a temperatura
real_data = df['temperatura'][-seq_length:]  # Últimos valores reais
forecast_temperature = forecast_1h[:, 0]     # Temperatura prevista

# Criação do gráfico
plt.figure(figsize=(10, 6))
plt.plot(real_data.index, real_data, label='Dados Reais (Temperatura)', color='blue')
future_dates = [real_data.index[-1] + datetime.timedelta(minutes=10 * (i + 1)) for i in range(6)]
plt.plot(future_dates, forecast_temperature, label='Previsão (Temperatura)', color='red', linestyle='--')
plt.xlabel('Data')
plt.ylabel('Temperatura (°C)')
plt.title('Previsão de Temperatura para 1 Hora à Frente')
plt.legend()
plt.grid(True)
plt.show()
