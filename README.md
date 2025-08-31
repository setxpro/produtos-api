# Produtos • Jakarta EE (WildFly) + Docker + Kubernetes

CRUD simples de **Produtos** usando **Jakarta EE** (JAX-RS + CDI + JPA/H2 por padrão), empacotado como **WAR** e executado no **WildFly**.  
Inclui instruções para rodar com **Docker** e **Kubernetes** (Docker Desktop e/ou Portainer).

> **Stack:** Java 17 • Jakarta EE 10 • WildFly 37 • JAX-RS (RESTEasy) • CDI • JPA (H2 Demo) • Maven

---

## 📦 Estrutura

```css
├─ src/main/java/com/produtos/
│ ├─ JaxrsConfig.java # @ApplicationPath("/api")
│ ├─ Produto.java # Entidade (com/sem @Entity)
│ ├─ ProdutoRepository.java # Repositório (memória ou JPA)
│ └─ ProdutoResource.java # REST /api/produtos
├─ src/main/webapp/index.html # UI simples (form + tabela)
├─ src/main/resources/META-INF/persistence.xml # (quando usar JPA)
├─ pom.xml
├─ Dockerfile
└─ k8s.yaml # Deployment + Service (NodePort)
```

---

## 🚀 Como rodar

### 1) Maven (gera WAR)

```bash
mvn clean package
# gera target/ROOT.war
```

### 2) Docker (local)

```sh
docker build -t produtos:1.0.0 .
docker run --rm -p 8080:8080 --name produtos produtos:1.0.0
```

#### Acesse:

[UI: http://localhost:8080/](http://localhost:8080/)

[API: GET http://localhost:8080/api/produtos](http://localhost:8080/api/produtos) 

## ☸️ Kubernetes (Docker Desktop/Portainer)

### 1) Pré-requisitos

- Docker Desktop com Kubernetes habilitado (contexto docker-desktop).
- kubectl instalado.

### 2) Usando kubectl

```bash
# build local (K8s do Docker Desktop vê imagens locais)
docker build -t produtos:1.0.0 .

# aplicar manifests
kubectl apply -f k8s.yaml

# ver pods/serviço
kubectl get pods,svc
```

[Acesse: http://localhost:31080/](http://localhost:31080/)

[API: http://localhost:31080/api/produtos](http://localhost:31080/api/produtos)

## 🧪 Endpoints (REST)

- GET /api/produtos — lista
- GET /api/produtos/{id} — detalhe
- POST /api/produtos — cria ({"nome","preco","estoque"})
- PUT /api/produtos/{id} — atualiza
- DELETE /api/produtos/{id} — remove

## 🗄️ Persistência

Por padrão o projeto roda com repositório em memória (demo).
Para persistência real, ative JPA:

1. Dependências no pom.xml:

```xml
<dependency>
  <groupId>jakarta.platform</groupId>
  <artifactId>jakarta.jakartaee-api</artifactId>
  <version>10.0.0</version>
  <scope>provided</scope>
</dependency>
```

2. src/main/resources/META-INF/persistence.xml apontando para java:/jboss/datasources/ExampleDS (H2 do WildFly).

3. Marcar Produto com @Entity e trocar o ProdutoRepository para usar EntityManager.

Em produção, troque para Postgres (ConfigMap/Secret + DataSource no WildFly ou persistence.xml com URL do banco).

## 🧰 Comandos úteis

```sh
# logs
kubectl logs -f deploy/produtos

# reiniciar com nova imagem
docker build -t produtos:1.1.0 .
kubectl set image deploy/produtos wildfly=produtos:1.1.0
kubectl rollout status deploy/produtos

# escalar
kubectl scale deploy/produtos --replicas=2

```
## 🐞 Troubleshooting

- NodePort não abre: kubectl get svc produtos (confira 80:31080/TCP).
- PowerShell -H no curl: use Invoke-RestMethod ou curl.exe.
- Pod CrashLoopBackOff por args: remova args se seu Dockerfile já define o CMD.
- Imagem não encontrada no K8s: mantenha imagePullPolicy: IfNotPresent e use o mesmo engine do Docker Desktop.
