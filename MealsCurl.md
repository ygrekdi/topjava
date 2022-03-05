Примеры запросов curl для meal rest api

#Получить всю еду
curl -v localhost:8080/topjava/rest/meals
#Получить еду по id
curl -v localhost:8080/topjava/rest/meals/100004
#Удалить еду по id
curl -X DELETE localhost:8080/topjava/rest/meals/100004
#Добавить новую еду
curl -X POST localhost:8080/topjava/rest/meals -H 'Content-type:application/json' -d '{"dateTime": "2022-04-03T09:06:29.645576", "description": "Lunch", "calories": "404"}'
#Обновить существующую еду
curl -X PUT localhost:8080/topjava/rest/meals/100011 -H 'Content-type:application/json' -d '{"dateTime": "2022-04-04T09:06:29.645576", "description": "Updated lunch", "calories": "200"}'
#Получить еду за определенное время
curl -v 'localhost:8080/topjava/rest/meals/between?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-30&endTime=12:00:00'