#!/bin/sh
docker compose up --abort-on-container-exit && docker compose down -v --remove-orphans
