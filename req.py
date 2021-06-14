import requests

with open('notificacoes.txt') as f:
   for line in f:
        if line == '\n': 
            continue
        myjson=eval(line)
        print('request ', myjson)
        response = requests.post('http://localhost:8080/subscriptions', json=myjson)
        print('response ', response.status_code)