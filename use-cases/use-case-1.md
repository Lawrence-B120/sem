# USE CASE: 1 Produce a Report on the details of a Country

## CHARACTERISTIC INFORMATION

### Goal in Context

As a user I want to create a country report so that I can support population reporting of the organization.
### Scope

Company

### Preconditions

We know the country. Database contains country data.

### Success End Condition

A report of the given country is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information of country
2. User enters code of desired country.
3. User extracts information of the specific country.
4. User provides report to requester.

## EXTENSIONS

2. **Country does not exist:**
    1. User informs requester that country does not exist in database.