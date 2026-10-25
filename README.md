# ADSS Group Project — Supermarket Management System

A Java management system for a supermarket chain, built for the **Analysis & Design of
Software Systems (ADSS)** course at Ben-Gurion University. The system follows a layered,
object-oriented architecture backed by an SQLite database, and was designed through domain
models, use-case tables, and sequence diagrams.

## About this project

This was a university **group project**. The overall system was split into four parts, and
each sub-group was responsible for one of them.

My partner and I were responsible for the **Employees** part. In a later stage we had to
integrate our module with the sub-group responsible for the **Transport** part — so beyond
building the Employees module itself, I also modified parts of the Transport module so the
two could work together cleanly (for example, keeping driver and employee records in sync
across both sides).

My work is spread across three branches:

- [`employee-part-dev`](../../tree/employee-part-dev) — building the Employees module
- [`db-integration-fixes`](../../tree/db-integration-fixes) — SQLite persistence and fixes
- [`employees-transport-integration`](../../tree/employees-transport-integration) — integrating Employees with Transport

## What I built

- Employee and shift management — the `EmployeeManager`, shift creation, and handling of
  past and upcoming shifts.
- The SQLite persistence layer for this part: `EmployeeDB` and `ShiftDB`, separating the
  generic `DBManager` from the entity-specific managers.
- The integration between the Employees and Transport modules, including the adjustments on
  the Transport side needed for a smooth connection.
- Design documentation and sequence diagrams for the Employees part.

The full commit history and every group member's contributions are preserved in this
repository.
