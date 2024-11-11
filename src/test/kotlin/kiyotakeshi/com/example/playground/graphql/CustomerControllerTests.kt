package kiyotakeshi.com.example.playground.graphql

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.HttpGraphQlTester

@SpringBootTest
@AutoConfigureHttpGraphQlTester
class CustomerControllerTests {

    @Autowired
    private lateinit var client: HttpGraphQlTester

    @Test
    fun `CustomersWithOrders N+1`() {
        this.client.documentName("schema")
            .operationName("CustomersWithOrdersNPlus1")
            .execute()
            .path("data.customersNPlus1")
            .matchesJsonStrictly(
                """
                [
                    {
                        "id": "1",
                        "name": "mike",
                        "age": 30,
                        "city": "New York",
                        "ordersNPlus1": [
                            {
                                "id": "1",
                                "description": "beer"
                            },
                            {
                                "id": "2",
                                "description": "book"
                            }
                        ]
                    },
                    {
                        "id": "2",
                        "name": "john",
                        "age": 25,
                        "city": "San Francisco",
                        "ordersNPlus1": [
                            {
                                "id": "3",
                                "description": "computer"
                            }
                        ]
                    },
                    {
                        "id": "3",
                        "name": "kanye",
                        "age": 40,
                        "city": "Chicago",
                        "ordersNPlus1": []
                    }
                ]
                """.trimIndent()
            )
    }

    @Suppress("complexity")
    @Test
    fun `CustomersWithOrders`() {
        this.client.documentName("schema")
            .operationName("CustomersWithOrders")
            .execute()
            .path("data.customers")
            .matchesJsonStrictly(
                """
                [
                    {
                        "id": "1",
                        "name": "mike",
                        "age": 30,
                        "city": "New York",
                        "orders": [
                            {
                                "id": "1",
                                "description": "beer",
                                "detail": {
                                    "id": "1",
                                    "price": 100.0,
                                    "productCode": "001",
                                    "quantity": 1
                                }
                            },
                            {
                                "id": "2",
                                "description": "book",
                                "detail": {
                                    "id": "2",
                                    "price": 200.0,
                                    "productCode": "002",
                                    "quantity": 2
                                }
                            }
                        ]
                    },
                    {
                        "id": "2",
                        "name": "john",
                        "age": 25,
                        "city": "San Francisco",
                        "orders": [
                            {
                                "id": "3",
                                "description": "computer",
                                "detail": {
                                    "id": "3",
                                    "price": 300.0,
                                    "productCode": "003",
                                    "quantity": 1
                                }
                            }
                        ]
                    },
                    {
                        "id": "3",
                        "name": "kanye",
                        "age": 40,
                        "city": "Chicago",
                        "orders": []
                    }
                ]
                """.trimIndent()
            )
    }
}
