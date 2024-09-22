# Projeto de Monitoramento Ambiental com ESP32 e API

Este projeto consiste no desenvolvimento de um sistema de monitoramento ambiental utilizando tecnologias modernas de software e hardware. O objetivo principal é capturar e exibir dados de luminosidade e temperatura de forma eficiente e em tempo real.

## Tecnologias Utilizadas

- **Backend**: A API foi desenvolvida utilizando [Spring Boot](https://spring.io/projects/spring-boot), uma estrutura robusta e popular para criação de APIs em Java, que permite a integração e o gerenciamento dos dados.
- **Banco de Dados**: Os dados capturados pelos sensores são armazenados em um banco de dados [MySQL](https://www.mysql.com/), garantindo uma organização eficiente e persistência das informações.
- **Frontend**: Para a interface de visualização dos dados, foi utilizado [React](https://react.dev/), uma biblioteca JavaScript amplamente usada para construção de interfaces de usuário dinâmicas e interativas.
- **Dispositivo IoT**: A programação do dispositivo responsável pela coleta dos dados foi feita em **C** no microcontrolador [ESP32](https://www.espressif.com/en/products/socs/esp32), que se conecta à API e envia os dados dos sensores.

- Link do FrontEnd [Front END](https://github.com/Erik-martins99/FrontEstacaoDeMonitoramentoClimatico)
  
## Equipamentos Utilizados

- **ESP32**: Microcontrolador utilizado para a leitura dos sensores e comunicação com a API.
- **Sensor LDR**: Sensor de luminosidade que mede a intensidade da luz.
- **Sensor de Temperatura**: Utilizado para medir a temperatura ambiente.
- **Protoboard**: Usada para a montagem do circuito dos sensores de forma simples e sem solda.
- **Resistores de 10K ohms**: Dois resistores foram utilizados para garantir o funcionamento adequado dos sensores.

## Funcionamento

O sistema funciona integrando o ESP32 com sensores de luminosidade e temperatura, que enviam os dados para a API via HTTP. Esses dados são então armazenados no banco de dados MySQL e podem ser visualizados em uma interface web desenvolvida com React.

![Painel De Monitoramento](https://github.com/user-attachments/assets/e4c726f9-53dd-4510-a557-b0b4cafd7df4)

![Diagrama](https://github.com/user-attachments/assets/173c095c-c104-4c18-a7c3-13af8678bd10)
Obs: O diagrama foi feito com arduino por conta que o tinkercad não oferece suporte para esp32.

## Como Executar

### Requisitos

- Spring Boot
- MySQL
- Node.js (para o frontend com React)
- ESP32 e componentes eletrônicos

### Instruções

1. Clone o repositório.
2. Configure o banco de dados MySQL.
3. Inicie o servidor Spring Boot para a API.
4. Inicie a aplicação frontend em React.
5. Programe o ESP32 com o código C para captar dados dos sensores e enviar para a API.

---

Este projeto é uma demonstração prática de como integrar tecnologias de software com dispositivos IoT, criando um sistema que monitora as condições ambientais e disponibiliza esses dados de maneira acessível e em tempo real via uma aplicação web.
