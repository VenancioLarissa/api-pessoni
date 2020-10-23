import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class TestaCliente {

    String enderecoApi = "http://localhost:8080";
    String endpoint = "/cliente";
    String endpointApagaTodos = "/apagaTodos";

    @Test
    @DisplayName("Quando pegar lista de clientes sem adicionar cliente, Então a lista deve estar vazia")
    public void pegaTodosClientes(){
        apagaTodosClientes();
        String respostaEsperada = "{}";

        given().
                contentType(JSON).
        when().
                get(enderecoApi).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));
    }
    @Test
    @DisplayName("Quando cadastrar um cliente, Então o cliente é cadastrado com sucesso")
    public void cadastrarNovoCliente(){

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Larissa\",\n" +
                " \"idade\": \"28\",\n" +
                " \"id\": \"9876\"\n" +
                "}";

        String respostaEsperada = "{\"9876\":" +
                "{\"nome\":\"Larissa\"," +
                "\"idade\":28," +
                "\"id\":9876," +
                "\"risco\":0}" +
                "}";

        given().
                contentType(JSON).
                body(corpoRequisicao).
        when().
                post(enderecoApi+endpoint).
        then().
                statusCode(201).
                assertThat().
                body(new IsEqual(respostaEsperada));
    }

    @Test
    @DisplayName("Quando atualizar um cliente, Então o cliente é atualizado com sucesso")
    public void atualizarUmCliente(){

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Larissa\",\n" +
                " \"idade\": \"28\",\n" +
                " \"id\": \"9876\"\n" +
                "}";

        String corpoAtualizadoRequisicao = "{\n" +
                " \"nome\": \"Larissa Venancio\",\n" +
                " \"idade\": \"27\",\n" +
                " \"id\": \"9876\"\n" +
                "}";

        String respostaEsperada = "{\"9876\":" +
                "{\"nome\":\"Larissa Venancio\"," +
                "\"idade\":27," +
                "\"id\":9876," +
                "\"risco\":0}" +
                "}";

        given().
                contentType(JSON).
                body(corpoRequisicao).
        when().
                post(enderecoApi+endpoint);

        given().
                contentType(JSON).
                body(corpoAtualizadoRequisicao).
        when().
                put(enderecoApi+endpoint).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));

        apagaTodosClientes();

    }

    @Test
    @DisplayName("Quando deletar um cliente, Então o cliente é deletado com sucesso")
    public void deletarUmCliente(){

        String idParaApagar = "/9876";

        String corpoRequisicao = "{\n" +
                " \"nome\": \"Larissa\",\n" +
                " \"idade\": \"28\",\n" +
                " \"id\": \"9876\"\n" +
                "}";

        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Larissa, IDADE: 28, ID: 9876 }";

        given().
                contentType(JSON).
                body(corpoRequisicao).
                when().
                post(enderecoApi+endpoint);

        given().
                contentType(JSON).
        when().
                delete(enderecoApi+endpoint+idParaApagar).
                then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));

    }

    public void apagaTodosClientes(){

        String respostaEsperada = "{}";

        given().
        when().
                delete(enderecoApi+endpoint+endpointApagaTodos).
        then().
                statusCode(200).
                assertThat().
                body(new IsEqual(respostaEsperada));

    }
}
