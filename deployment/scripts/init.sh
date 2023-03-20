#!/bin/sh

# Initialize terraform remote postgres backend

heroku create "$TERRAFORM_BACKEND" && /
heroku addons:create heroku-postgresql:mini --app "$TERRAFORM_BACKEND"