# Sports Match Printer

## Overview
The Sports Match Printer is a Spring Boot application designed to fetch sports match details and print them to the console. It supports various sports, including football, basketball, tennis, and hockey. The application retrieves data from a sports betting API and displays it in a structured format, including match names, times, and betting odds.

## Features
- Fetches sports data from a remote API.
- Prints match details for top leagues.
- Displays odds for different betting markets.
- Supports multiple sports including football, basketball, tennis, and hockey.
- Implements multithreading for efficient data fetching.
- Configurable via `application.properties`.

## Technologies Used
- Java 17
- Spring Boot
- Spring WebFlux (or Spring Web if you choose)
- Maven for dependency management

## Getting Started

### Prerequisites
- Java 17 installed on your machine.
- Maven installed for building the project.

### Clone the Repository
```bash
git clone https://github.com/yourusername/sports-match-printer.git
cd sports-match-printer
