# ISA

Student project on Internet Software Architecture course at University of Novi Sad, Faculty of Technical Science.

Uber like application written in Java Spring Boot + React.JS.

## Team 31

| Contributor                                   | Full name                | Student Id |
| --------------------------------------------- | ------------------------ | ---------- |
| [erosdavid](https://github.com/erosdavid)     | David Ereš               | R2 36/2021 |
| [djolewalker](https://github.com/djolewalker) | Dimitrije Žarković Đolai | R2 17/2021 |


## Development environments:

- Server application: [DigitalOcean](https://isa-server-bf2b5.ondigitalocean.app/swagger-ui/index.html)
- Client application: [GitHub Pages](https://djolewalker.github.io/ISA)


## Requirements

- PostgreSQL 12+
- Node 16.20


## Commit convention

In this repository we are using [Conventional commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4).

Please **follow the connvention**. Package versions are build on commit messages base.

Example:
- Feature like "home page" thet affect only spa application:
  ```
  feat(isa-spa): home page implemented
  ```

- Bugfix about field validation - affects both spa and server application:
  ```
  fix: field validation has wrong messages
  ```
