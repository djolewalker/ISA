#!/bin/sh

cd terraform || exit && /
DATABASE_URL=$(heroku config:get DATABASE_URL --app "$TERRAFORM_BACKEND") && export DATABASE_URL && /
terraform init -backend-config="conn_str=$DATABASE_URL" && /
terraform destroy -auto-approve -var isa_name="$APP_NAME" -var frontend_isa_name="${GATEWAY_NAME}" 