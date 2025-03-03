#!/bin/bash

echo "🚀 Iniciando el despliegue del servicio de customer..."

echo "🧹 Limpiando contenedores y volúmenes anteriores..."
docker-compose down -v
docker system prune -f

echo "🏗️  Construyendo y levantando los servicios..."
docker-compose up --build -d

echo "📝 Mostrando logs de los servicios..."
echo "Presiona Ctrl+C para detener la visualización de logs (los servicios seguirán ejecutándose)"
docker-compose logs -f

# Comandos útiles (comentados):
# Para detener los servicios: docker-compose down
# Para ver el estado de los servicios: docker-compose ps
# Para ver logs específicos:
#   - Customer Service: docker-compose logs -f customer-service
#   - PostgreSQL: docker-compose logs -f postgres 