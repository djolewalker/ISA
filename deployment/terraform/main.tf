terraform {
  backend "pg" {
  }
}

provider "heroku" {
}

variable "isa_name" {
  description = "Unique name of the ISA app"
}

variable "frontend_isa_name" {
  description = "Unique name of the FE ISA app"
}

resource "heroku_config" "isa_app" {
  vars = {
    STAGE = "test"
    CORS_ORIGIN = "https://${heroku_app.gateway.name}.herokuapp.com"
  }
}

resource "heroku_app" "isa_app" {
  name   = var.isa_name
  region = "eu"
  stack  = "container"
}

resource "heroku_build" "isa_app" {
  app_id = heroku_app.isa_app.id

  source {
    path = "isa_app"
  }
}

resource "heroku_app_config_association" "isa_app" {
  app_id = heroku_app.isa_app.id

  vars = heroku_config.isa_app.vars
}

resource "heroku_addon" "database" {
  app_id  = heroku_app.isa_app.id
  plan = "heroku-postgresql:hobby-dev"
}

resource "heroku_config" "gateway" {
  vars = {
    API_URL = "${heroku_app.isa_app.name}.herokuapp.com"
  }
}

resource "heroku_app" "gateway" {
  name   = var.frontend_isa_name
  region = "eu"
  stack  = "container"
}

resource "heroku_app_config_association" "gateway" {
  app_id = heroku_app.gateway.id

  vars = heroku_config.gateway.vars
}

resource "heroku_build" "gateway" {
  app_id = heroku_app.gateway.id

  source {
    path = "isa_app_front"
  }
  depends_on = [
    null_resource.gateway_build
  ]
}

data "template_file" "gateway_build" {
  template = file("${path.module}/isa_app_front/heroku.tpl")
  vars = {
    api_url = "\\\"'https://${heroku_app.isa_app.name}.herokuapp.com/api'\\\""
  }
}

resource "null_resource" "gateway_build" {
  triggers = {
    template = data.template_file.gateway_build.rendered
  }

  provisioner "local-exec" {
    command = "echo \"${data.template_file.gateway_build.rendered}\" > ${path.module}/isa_app_front/heroku.yml"
  }
}

output "isa_url" {
  value = "https://${heroku_app.isa_app.name}.herokuapp.com"
}
output "isa_front_url" {
  value = "https://${heroku_app.isa_app_front.name}.herokuapp.com"
}