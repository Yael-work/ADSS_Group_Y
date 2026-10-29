# Supermarket Management System (ADSS Group Project)

A Java management system for a supermarket chain, built for the Analysis & Design of
Software Systems (ADSS) course at Ben-Gurion University. The system uses a layered,
object-oriented architecture backed by an SQLite database, and was designed through domain
models, use-case tables, and sequence diagrams.

## About this project

This was a university group project. The overall system was split into four parts, and each
sub-group was responsible for one of them.

My partner and I were responsible for the Employees part. In a later stage we connected our
module to the Transport part, which another sub-group built, and that meant adjusting some
of the Transport code so the two sides worked together cleanly (for example, keeping driver
and employee records in sync).

Our work is on the
[`employees-transport-integration`](../../tree/employees-transport-integration) branch. The
`main` branch holds the full system after all the sub-groups' parts were integrated.

## What I built

- Employee and shift management: the `EmployeeManager`, shift creation, and the handling of
  past and upcoming shifts.
- The SQLite persistence layer for this part, including `EmployeeDB`, `ShiftDB`, and the
  split between the generic `DBManager` and the entity-specific managers.
- The connection between the Employees and Transport modules, including the changes on the
  Transport side needed for it to work smoothly.
- Design documentation and sequence diagrams for the Employees part.

The full commit history and every group member's contributions are preserved in this
repository.
