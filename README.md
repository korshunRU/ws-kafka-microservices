# Постановка задачи
Создать три взаимодействующих между собой микросервиса МС1, МС2 и МС3. 
Микросервисы взаимодействую между собой следующим образом:

1. МС1 создает сообщение следующего формата:
```
{
    "id": integer.
    "session_id": integer,
    "MC1_timestamp": datetime,
    "MC2_timestamp": datetime,
    "MC3_timestamp": datetime,
    "end_timestamp": datetime
}
```
где "session_id" - номер сеанса взаимодействия. 
В поле “MC1_timestamp” записывается текущее время и отправляется 
сообщение в МС2 через WebSocket

2. МС2 принимает сообщение от МС1, записывает в поле сообщения
"МС2_timestamp" текущее время и отправляет сообщение в МС3 через топик 
брокера Kafka
3. МС3 принимает сообщение от МС2, записывает в поле сообщения 
"МС3_timestamp" текущее время и отправляет сообщение в МС1 посредством 
отправки http запроса POST с телом, содержащим сообщение
4. МС1 принимает сообщение от МС3, записывает в поле "end_timestamp" 
текущее время, записывает сообщение в базу данных
5. Повторить цикл взаимодействия в течение заданного интервала взаимодействия

* Длительность интервала взаимодействия задается в секундах параметром в 
конфигурационном файле.

* В качестве БД использовать СУБД MariaDB. После остановки контейнеров с 
микросервисами и окружением база данных должна быть доступна для просмотра 
средствами СУБД.

* Старт взаимодействия осуществить отправкой запроса GET на /start/ без 
параметров в МС1.
* Досрочную остановку взаимодействия осуществить отправкой запроса GET 
на /stop/ без параметров в МС1.
* Начало и завершение взаимодействия микросервисов индицировать на консоль.
