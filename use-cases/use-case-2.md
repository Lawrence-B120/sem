# USE CASE: 2 Produce a Report on the Details of a City

## CHARACTERISTIC INFORMATION

### Goal in Context

As a user i want to create a city report so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

We know the city's ID. Database contains city data.

### Success End Condition

A report of the given city is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information of city
2. User enters ID of city.
3. User extracts information of the specific city.
4. User provides report to requester.

## EXTENSIONS

3. **City does not exist:**
    1. User informs requester that city given does not exist in database.