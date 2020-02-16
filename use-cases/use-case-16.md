# USE CASE: 16 Produce a Report on All Cities in a Country

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list All the cities in a country organised by largest population to smallest so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains city data.

### Success End Condition

A report of all cities in a specified country is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on all cities in a specified country
2. User enters country name.
3. User extracts information on cities.
4. User provides report to requester.

## EXTENSIONS

3. **Country does not exist:**
    1. User informs requester that country given does not exist in database.