# USE CASE: 3 Produce a Report on the details of a Capital-City

## CHARACTERISTIC INFORMATION

### Goal in Context

As a user i want to create a capital city report so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

We know the capital-city's country. Database contains city data.

### Success End Condition

A report of the given capital-city is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information of capital-city
2. User enters code of country for desired capital-city.
3. User extracts information of the specific capital-city.
4. User provides report to requester.

## EXTENSIONS

2. **Country does not exist:**
    1. User informs requester that country given does not exist in database.