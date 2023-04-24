# app-entregas

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite o site: https://quarkus.io/ .

## Executando o aplicativo no modo dev

Você pode executar este aplicativo no modo dev que permite a codificação ao vivo usando:
```shell script
cd api/
./mvnw compile quarkus:dev

cd authorization/
./mnvw compile quarkus:dev -Ddebug=5006

utilizando linux, só seguir estes passos:
no diretório app-entrega/
sudo chmod +x generatejwtKeys.sh (para dar permissão de execução ao arquivo)
./generatejwtKeys.sh (executar arquivo para gerar chaves de seguraça para o token jwt)

utilizando windows, gerar manualmente as chaves:
https://quarkus.io/guides/security-jwt
```

> **_Indicação de fluxo para verificação de todo funcionando da aplicação: <br>_**  
>1. Acessar a documentação do serviço de autenticação http://localhost:8082/doc/swagger
>   1. criar um usuário com a role "ADMIN";
>   2. efetuar login com as credênciais do mesmo;
>   3. copiar o token JWT
>4. Acessar a documentação da API de gerenciamento de entregas http://localhost:8081/doc/swagger
>   1. na sessão "Authorize" passar token gerado pelo sistema de autenticação
>   2. testar todas funcionalidades da API

## Empacotando e executando o aplicativo

O aplicativo pode ser empacotado usando:
```shell script
./mvnw package
```
Ele produz o arquivo `quarkus-run.jar` no diretório `target/quarkus-app/`.
Esteja ciente de que não é um _über-jar_ pois as dependências são copiadas para o diretório `target/quarkus-app/lib/`.

O aplicativo agora pode ser executado usando `java -jar target/quarkus-app/quarkus-run.jar`.

Se você deseja construir um _über-jar_, execute o seguinte comando:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

O aplicativo, empacotado como um _über-jar_, agora pode ser executado usando `java -jar target/*-runner.jar`.
## Creating a native executable

Você pode criar um executável nativo usando:
```shell script
./mvnw package -Pnative
```

Ou, se você não tiver o GraalVM instalado, pode executar o build executável nativo em um contêiner usando: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./target/app-entregas-1.0.0-SNAPSHOT-runner`

Se você quiser saber mais sobre a criação de executáveis nativos, consulte https://quarkus.io/guides/maven-tooling.
## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
