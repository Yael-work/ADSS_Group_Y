# Supermarket Management System (ADSS Group Project)

A Java management system for a supermarket chain, built for the Analysis & Design of
Software Systems (ADSS) course at Ben-Gurion University. The system uses a layered,
object-oriented architecture backed by an SQLite database, and was designed through domain
models, use-case tables, and sequence diagrams.

## About this project

This was a large university group project. The full system was split into four parts, and
each sub-group was responsible for one of them. My partner and I were responsible for the
Employees part.

In a later stage we connected our Employees module to the Transport part, which another
sub-group built. That meant adjusting some of the Transport code so the two sides worked
together cleanly, for example keeping driver and employee records in sync.

## What this repository shows

This repository holds only my part of a much larger project. The other three parts were
built by other sub-groups and are not published here. While we worked, the project had many
branches, one for each sub-group and development stage. I kept only the branch that holds my
work after the Employees and Transport parts were integrated:

- [`employees-transport-integration`](../../tree/employees-transport-integration) is my
  Employees work after it was integrated with the Transport part.
- `main` is the shared baseline the sub-groups built on.

So what you see here is my slice of the system, not the full group project.

## What I built

- Employee and shift management: the `EmployeeManager`, shift creation, and the handling of
  past and upcoming shifts.
- The SQLite persistence layer for this part, including `EmployeeDB`, `ShiftDB`, and the
  split between the generic `DBManager` and the entity-specific managers.
- The connection between the Employees and Transport modules, including the changes on the
  Transport side needed for it to work smoothly.
- Design documentation and sequence diagrams for the Employees part.

The commit history on my branch preserves my contributions and my partner's.
