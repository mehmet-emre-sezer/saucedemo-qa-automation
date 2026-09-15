# Requirements Specification — SauceDemo

**Application Under Test (AUT):** https://www.saucedemo.com
**Author:** Mehmet Emre Sezer
**Version:** 1.0
**Scope:** Login · Products (Inventory) · Cart · Checkout

> Each requirement has a unique ID (`REQ-<AREA>-<NNN>`). Test cases and automated
> tests are traced back to these IDs in the Requirements Traceability Matrix (RTM).

---

## 1. Login

| ID | Requirement | Priority | Type |
|----|-------------|----------|------|
| REQ-LOGIN-001 | The user shall be able to log in with a valid username and password. | High | Positive |
| REQ-LOGIN-002 | When invalid credentials are entered, the system shall display an error message. | High | Negative |
| REQ-LOGIN-003 | When a locked-out user attempts to log in, the system shall display a "locked out" error message. | Medium | Negative |
| REQ-LOGIN-004 | When the username or password field is empty, the system shall display a mandatory-field error message. | Medium | Negative |
| REQ-LOGIN-005 | After a successful login the user shall be redirected to the Products (Inventory) page. | High | Positive |

## 2. Products (Inventory)

| ID | Requirement | Priority | Type |
|----|-------------|----------|------|
| REQ-PROD-001 | The Products page shall list all available products with name, price and image. | High | Positive |
| REQ-PROD-002 | The user shall be able to sort products (Name A–Z, Z–A, Price low–high, high–low). | Medium | Positive |
| REQ-PROD-003 | The user shall be able to add a product to the cart from the Products page, and the cart badge count shall increase. | High | Positive |
| REQ-PROD-004 | The user shall be able to remove a product from the cart directly on the Products page. | Medium | Positive |

## 3. Cart

| ID | Requirement | Priority | Type |
|----|-------------|----------|------|
| REQ-CART-001 | A product added to the cart shall appear on the Cart page with the correct name and price. | High | Positive |
| REQ-CART-002 | The user shall be able to remove a product from the Cart page. | High | Positive |
| REQ-CART-003 | The "Continue Shopping" button shall return the user to the Products page. | Low | Positive |
| REQ-CART-004 | The "Checkout" button shall take the user to the checkout information page. | High | Positive |

## 4. Checkout

| ID | Requirement | Priority | Type |
|----|-------------|----------|------|
| REQ-CHECKOUT-001 | The user shall be able to enter first name, last name and postal code and continue to the overview page. | High | Positive |
| REQ-CHECKOUT-002 | When a required checkout field is missing, the system shall display an error message. | High | Negative |
| REQ-CHECKOUT-003 | The checkout overview page shall display the selected items and the total price. | Medium | Positive |
| REQ-CHECKOUT-004 | After finishing the order, the system shall display an order-confirmation message. | High | Positive |

---

## Test Users (SauceDemo built-in)

| Username | Purpose |
|----------|---------|
| `standard_user` | Happy-path (positive) scenarios |
| `locked_out_user` | Locked-out negative scenario (REQ-LOGIN-003) |
| `problem_user` | UI-defect scenarios (optional, future scope) |

Password for all users: `secret_sauce`
