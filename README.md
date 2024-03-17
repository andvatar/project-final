## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:  
2 Выполнено.  
3 Выполнено.  
4 Выполнено.  
5 Выполнено.  
6 Выполнено.  
7 Выполнено. Добавлены методы addTag в TaskService и TaskController    
8 Выполнено. Я предположил, что задача может возвращать с тестирования много раз, поэтому суммирую время с попадания на стадию до перехода на любую другую стадию. Для тестирования добавил методы контроллера getTimeInProgress и getTimeInTesting.    
9 Выполнено.  
10 Выполнено. Добавить ресурсы в jar не получилось, копирую в docker file.  