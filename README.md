# Тестовое задание на позицию Java-разработчик
---
Создать систему записи пациентов на приём, состоящую из:
1. Базы данных (желательно PostgreSQL)
    1. С таблицами
        1. врачи: id, uuid, ФИО и т. д. (здесь и далее «и т. д.» обозначает, что можно расширить структуру, если будут свои идеи)
        2. пациенты: id, uuid, ФИО, дата рождения и т. д.
        3. талон (слот времени): id, id врача, id пациента, время начала приёма и т. д.
    2. SQL-скрипт создания структура этой базы в выбранной СУБД (можно взять автоматически сгенерированный скрипт по созданной структуре).
2. Java web приложение, выполняющее основную логику (желательно Spring Boot)
    1. Осуществлять подключение и операции в БД (желательно средствами Java Persistence API)
    2. SOAP сервис с методом создания расписания, который по переданным правилам создаёт слоты времени. Правила могут состоять из даты/времени начала расписания, продолжительность талона (слота времени), их количество и т. д. Можно применить более сложную структуру правил, если будет желание.
    3. REST сервис работы с методами:
        1. получение свободных слотов времени к указанному врачу на указанную дату
        2. занятие слота времени по его id
        3. получение всех слотов времени, занятых одним пациентом по id/uuid

#### Используемые инструменты:
1. Spring Boot.
2. СУБД PostgreSQL.
3. Spring Data JPA, Hibernate.
4. Spring Web Services, SOAP. 
4. Swagger.
5. Сборщик проектов `maven`.

#### Особенности проекта
1. Проект содержит скрипт создания стурктуры БД (`/src/main/resources/shedule.sql`).
2. Проект содержит страницу с документацией на REST-endpoint Swagger-UI по адресу http://localhost:8080/swagger-ui/index.html
3. Параметры соединения с базой данных указаны в `/src/main/java/com/testtask/clinic/database/config/DatabaseConnection.java`
4. Схема БД:

```
+----------+------
| DOCTORS        |
+----------+------
| ID       | PK  |1-------------|
| UUID     |     |              |
| NAME     |     |              |
+----------+-----|              |
                                |
+--------------+------          |
| PATIENTS           |          |
+--------------+------          |
| ID           | PK  |1---|     |
| UUID         |     |    |     |
| NAME         |     |    |     |
| DATE_OF_BIRTH|     |    |     |
+--------------+------    |     |
                          |     |
                          |     |
+--------------+------    |     |
| TIMESLOTS          |    |     |
+--------------+------    |     |
| ID           | PK  |    |     |
| PATIENT_ID   | FK  |*---|     |
| DOCTOR_ID    | FK  |*---------|
| STARTTIME    |     |
| VISITTTIME   |     |
+--------------+------

 ```
#### Описание REST-сервиса

```
GET
/slot/get/free?doctorId={integer}&dateString={string}
Получить свободные слоты времени для указанного доктора на указанную дату
Пример:
/slot/get/free?doctorId=1&dateString=2023-09-19

GET
/slot/get/occupied/id?patientId={integer}
Получить слоты времени, занятые указанным пациентом по id
Пример:
/slot/get/occupied/id?patientId=1

GET
/slot/get/occupied/uuid?uuid={string}
Получить слоты времени, занятые указанным пациентом по uuid
Пример:
/slot/get/occupied/uuid?uuid=e7077bea-5249-4bc8-b491-9aec257b3934

POST
/slot/occupy?slotId={integer}&patientId={integer}
Занять слот времени по id пациента и id слота
Пример:
/slot/occupy?slotId=10&patientId=1
```
#### Описание SOAP-сервиса

Адрес для SOAP-запросов:
`http://localhost:8080/makeShedule`
Параметры запроса на создание расписания:

```
doctor_id					: id врача,
date						: дата, на которую формируется расписание,
startTime					: время начала приема врача,
finishTime					: время окончания примема врача,
intervalTimeSlotMinutes	: планируемая длительность приема в минутах,
timeSlotsNumber			: планируемое количество принятых пациентов.
```
Ограничения на параметры: если расписание составляется исходя из планируемого времени приема, параметр `timeSlotsNumber` должен быть равен нулю. Если расписание составляется исходя из планируемого количества принятых пациентов, параметр `intervalTimeSlotMinutes` должен быть равен нулю.

Пример запроса на создание расписания:

```
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://www.testtask.com/clinic/soap/gen">
    <soapenv:Header/>
    <soapenv:Body>
        <gs:makeSheduleRequest>
            <gs:doctor_id>1</gs:doctor_id>
            <gs:date>2023-09-19</gs:date>
            <gs:startTime>09:00:00</gs:startTime>
            <gs:finishTime>15:00:00</gs:finishTime>
            <gs:intervalTimeSlotMinutes>0</gs:intervalTimeSlotMinutes>
            <gs:timeSlotsNumber>8</gs:timeSlotsNumber>
        </gs:makeSheduleRequest>
    </soapenv:Body>
</soapenv:Envelope>
```

#### Как запустить проект

1. Импортировать maven-проект в IDE.
2. Убедиться, что установленная JDK не ниже 17
3. Собрать проект выполнием команды `mvn clean install`;
4. Запустить на исполнение приложение `src/main/java/com/testtask/clinic/ClinicApplication.java`
5. Перейти на страницу с документацией localhost:8080/swagger-ui/index.html с локального компьютера.
