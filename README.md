# OPD-Token-Allocation-Engine

Project Overview
This project is a dynamic token allocation system designed for hospital Outpatient Departments (OPD). It manages doctor time slots with elastic capacity, ensuring that high-priority patients and emergencies are handled effectively even when slots reach their standard limits.

Core Features
. Per-Slot Hard Limits: Enforces a maximum patient capacity for each fixed doctor time slot (e.g., 9:00 - 10:00)
. Dynamic Prioritization: Automatically reorders the patient queue based on the source of the booking
. Elastic Capacity: Dynamically reallocates or adds tokens to handle emergency insertions and real-world variability
. Source Management: Supports multiple token sources including Online, Walk-in, Paid Priority, and Follow-ups

Prioritization Logic
The system uses a weighted algorithm to prioritize patients within the same time slot. Lower weights indicate higher priority:

| Patient Source | Priority Weight | Description                                            |
| -------------- | --------------- | ------------------------------------------------------ |
| Emergency      | 1               | Immediate addition; bypasses standard capacity limits. |
| Paid Priority  | 2               | High-priority patients who jump the standard queue.    |
| Follow-up      | 3               | Returning patients for existing consultations.         |
| Online Booking | 4               | Standard pre-booked appointments.                      |
| Walk-in        | 5               | Patients arriving directly at the OPD desk.            |

API Design & Deliverables
The system is built as an API-based service.
Key Endpoints:
.POST /api/tokens/book: Allocates a new token based on availability and priority.
.GET /api/tokens/view/{slotId}: Retrieves the prioritized schedule for a specific doctor's slot.
.PATCH /api/tokens/{id}/cancel: Handles cancellations and frees up slot capacity.

Failure Handling & Edge Cases 
. Cancellations & No-shows: When a token is cancelled, the system immediately updates the "active" count for that slot, allowing for new walk-ins or priority reallocations.

. Emergency Over-provisioning: If an emergency occurs in a full slot, the system uses "elastic" logic to insert the patient at the top of the queue without deleting existing bookings.

. Database Persistence: Using PostgreSQL ensures that token data is preserved during power failures or server restarts.

Simulation: One Day in the OPD 
The system was tested with a simulation of 3 doctors:
1.Dr. Smith: Capacity 5.
2.Dr. Jones: Capacity 4.
3.Dr. Taylor: Capacity 6

Scenario: * Dr. Smith's slot is filled with 5 Online Bookings.
. An Emergency occurs.
. The system accepts the Emergency token and places it at position #1, demonstrating elastic capacity management.
