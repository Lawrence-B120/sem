# USE CASE: 9 Produce a Report on All Countries in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list All the countries in a region organised by largest population to smallest so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains country data.

### Success End Condition

A report of all countries in a specific region is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on all countries in a region.
2. User enters region name.
3. User extracts information on countries.
4. User provides report to requester.

## EXTENSIONS

3. **Region does not exist:**
    1. User informs requester that region given does not exist in database.