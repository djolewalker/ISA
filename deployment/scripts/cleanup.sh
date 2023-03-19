#!/bin/sh

# Destroy terraform backend on heroku postgres

heroku apps:destroy --app "$TERRAFORM_BACKEND" --confirm "$TERRAFORM_BACKEND"