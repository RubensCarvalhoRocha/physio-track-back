comandos para reativar a api:
heroku ps:scale web=1 -a physio-track-api
heroku addons:create heroku-postgresql:hobby-dev -a physio-track-api
