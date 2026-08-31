# StockRoom

Inventory and checkout for a small team (IT closet, lab, office equipment).

People take items off the shelf, they are due back on a date, and returning them puts stock back. **Admin** manages the catalog. **Staff** checks items in and out. The shelf quantity is the source of truth: you cannot check out more than is on hand.

Built as a Java full-stack portfolio project (Spring Boot + Thymeleaf).

## Demo logins

Open [http://localhost:8080](http://localhost:8080) after the app starts.

| Username | Password | What they can do |
| --- | --- | --- |
| `admin` | `admin123` | Everything staff can do, plus add/edit items |
| `staff` | `staff123` | View items, check out, return |

Demo-only accounts. Do not reuse these passwords on a real public site.

## Run it (Windows)

This project expects **Java 21**. On this machine the default `java` command may still be Java 8, so use the script (it points at Amazon Corretto 21):

```powershell
cd path\to\stockroom
.\run.ps1
```

Wait until the log says `Started StockRoomApplication`, then open [http://localhost:8080](http://localhost:8080).

Stop the app with **Ctrl+C** in that terminal.

You do not need to install Maven. `mvnw.cmd` (the Maven Wrapper) downloads it.

Tests:

```powershell
.\mvnw.cmd test
```

## What to click (2-minute walkthrough)

1. Log in as `staff`.
2. Home shows open checkouts, overdue loans, and how many items exist.
3. **Items** — catalog and quantities. Staff cannot add or edit.
4. **Checkouts** — who has what. Return puts stock back on the shelf.
5. Try checking out more units than quantity on hand. You should get an error, not a silent bad quantity.
6. Log out, log in as `admin`, add or edit an item.

## How the code is shaped

One app (not microservices). Packages:

| Package | Job |
| --- | --- |
| `domain` | Items, checkouts, users, stock rules |
| `service` | Checkout and catalog rules (`@Transactional` so stock and the loan stay in sync) |
| `web` | Pages (controllers + Thymeleaf templates) |
| `data` | Spring Data repositories (talk to the database) |
| `config` / `security` | Login, roles, password hashing |
| `db/migration` | Flyway SQL that creates tables |

Database today is **H2** (a file under `data/`). That is enough to learn and demo. PostgreSQL would be the usual next step for a production-like setup.

## Tech

- Java 21
- Spring Boot 4
- Spring Security (form login, `ADMIN` / `STAFF`)
- Spring Data JPA + Flyway
- Thymeleaf + Bootstrap 5
- JUnit (stock rules: cannot oversell, inactive items cannot go out)

## What I would add next

- PostgreSQL + Docker Compose
- Email reminders for overdue loans
- Audit log of who changed stock
- A REST API if a React UI were added later

## GitHub later

A **repository** is this project folder plus its history (commits). GitHub is a website that hosts that folder so others can see it. The README is the first page people read when they open the repo. This file is that page, even before anything is uploaded.
