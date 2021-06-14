#Decisões

<hr>

A decisão de criar o producer e o consummer no mesmo jar foi por conta da velocidade que eu ganharia no desenvolvimento.

Trabalhei principalmente com o uso de eventos do spring, pois assim consigo concentrar a lógica desse evento em um local apenas deixando os services, controllers, handles com menos responsabilidades.

O uso do banco PostgreSQL vem de sua segunrança para a integridade de dados e sua tolerancia a falhas.

As tabelas do banco são montadas pela aplicação em um script sql chamado schema.sql, o uso disso foi por conta da facilidade e do suporte do spring ao mesmo.

O uso do docker-compose para iniciar o banco de dados e aplicação foi feito por conta da facilida para subir as dependencias necessarias para a aplicação.

Com base em alguns testes acabei decidindo em criar os registros das assinaturas a partir das chamadas, pressupondo q esses ids viriam de outras aplicações, foi feito o teste com um seeding, todavia não achei satisfatório resultado inicial

<hr>



