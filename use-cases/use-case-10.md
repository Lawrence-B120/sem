# USE CASE: 10 Produce a Report on Top [N] Most Populated Countries

## CHARACTERISTIC INFORMATION

### Goal in Context

As a User i want to list The top N populated countries in the world where N is provided by the user so that I can support population reporting of the organization.

### Scope

Company

### Preconditions

Database contains country data.

### Success End Condition

A report of top N most populated countries is produced.

### Failed End Condition

No report is produced.

### Primary Actor

User

### Trigger

A request for population information is sent to the user

## MAIN SUCCESS SCENARIO

1. Request is made for information on top N most populated countries.
2. User enters number of desired entries.
3. User extracts information on countries.
4. User provides report to requester.

## EXTENSIONS

2. **Number of entries invalid:**
    1. User is informed that their entry was invalid.