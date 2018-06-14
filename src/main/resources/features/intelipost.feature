Feature: Intelipost test

Background:
	Given I set header "api-key" as "4aa90b1087807b5fb8e52b01584f84e416ddb8ab8e5b800ae5d0f075a2d1e379"
	  And I set header "platform" as "intelipost-docs"
	  And I set header "platform-version" as "v1.0.0"
	  And I set header "plugin" as "intelipost-plugin"
	  And I set header "plugin-version" as "v2.0.0"

  
  Scenario Outline: POST - quote_by_product - testes positivos - <testDescription>
    Given I use the route "/quote_by_product"
    And I use the json body
      """
       {
		  "origin_zip_code": "<origin_zip_code>",
		  "destination_zip_code": "<destination_zip_code>",
		  "quoting_mode": "DYNAMIC_BOX_ALL_ITEMS",
		  "products": [
		    {
		      "weight": 5,
		      "cost_of_goods": 10.7,
		      "width": 15,
		      "height": 17.5,
		      "length": 15,
		      "quantity": 1,
		      "sku_id": "SKU123",
		      "product_category": "Bebidas"
		    },
		    {
		      "weight": 7,
		      "cost_of_goods": 20.99,
		      "width": 20.5,
		      "height": 30.7,
		      "length": 20,
		      "quantity": 1,
		      "sku_id": "<sku_id>",
		      "product_category": "Bebidas"
		    }
		  ],
		  "additional_information": {
		    "lead_time_business_days": 1,
		    "sales_channel": "<sales_channel>",
		    "client_type": "gold"
		  },
		  "identification": {
		    "session": "04e5bdf7ed15e571c0265c18333b6fdf1434658753",
		    "ip": "000.000.000.000",
		    "page_name": "carrinho",
		    "url": "http://www.intelipost.com.br/checkout/cart/"
		  }
		}
      """
    When I send the POST request
    Then Http response should be <responseCode>
     And the response JSON must have "/status" as "<status>"

    Examples:
      | testDescription            | origin_zip_code | destination_zip_code | sku_id | sales_channel       | responseCode | status |
      | Exemplo                    | 04012080        | 04304011             | SKU456 | meu_canal_de_vendas | 200          | OK     |
      | Palmas (TO) - SC interior  | 77001-026       | 88470-000            | SKU456 | CN200               | 200          | OK     |
      | Interior tocantinense - SC | 77300000        | 88058-115            | SKU456 | CN200               | 200          | OK     |
      | Palmas (TO) - RS interior  | 77001-026       | 94901-970            | SKU456 | CN200               | 200          | OK     |
      | Interior tocantinense - RS | 77300000        | 91787-001            | SKU456 | CN200               | 200          | OK     |
      

  Scenario Outline: POST - quote_by_product - testes positivos - <testDescription>
	Given I use the route "/quote_by_product"
      And I use the json body
      """
       {
		  "origin_zip_code": "<origin_zip_code>",
		  "destination_zip_code": "<destination_zip_code>",
		  "quoting_mode": "DYNAMIC_BOX_ALL_ITEMS",
		  "products": [
		    {
		      "weight": 5,
		      "cost_of_goods": 10.7,
		      "width": 15,
		      "height": 17.5,
		      "length": 15,
		      "quantity": 1,
		      "sku_id": "SKU123",
		      "product_category": "Bebidas"
		    },
		    {
		      "weight": 7,
		      "cost_of_goods": 20.99,
		      "width": 20.5,
		      "height": 30.7,
		      "length": 20,
		      "quantity": 1,
		      "sku_id": "<sku_id>",
		      "product_category": "Bebidas"
		    }
		  ],
		  "additional_information": {
		    "lead_time_business_days": 1,
		    "sales_channel": "<sales_channel>",
		    "client_type": "gold"
		  },
		  "identification": {
		    "session": "04e5bdf7ed15e571c0265c18333b6fdf1434658753",
		    "ip": "000.000.000.000",
		    "page_name": "carrinho",
		    "url": "http://www.intelipost.com.br/checkout/cart/"
		  }
		}
      """
    When I send the POST request
    Then Http response should be <responseCode>
     And the response JSON must have "/status" as "<status>"
	 And The response JSON list node "/content/delivery_options" must not contain any key "delivery_method_name" as "Correios PAC"

    Examples:
      | testDescription         | origin_zip_code | destination_zip_code | sku_id | sales_channel | responseCode | status |
      | SKU123 - Sem PAC        | 04012080        | 04304011             | SKU123 | CN200         | 200          | OK     |
      | CN123 primeiro da faixa | 04012080        | 22710010             | SKU456 | CN123         | 200          | OK     |
      | CN123 dentro da faixa   | 04012080        | 22710334             | SKU456 | CN123         | 200          | OK     |
      | CN123 último da faixa   | 04012080        | 22710990             | SKU456 | CN123         | 200          | OK     |
      
      


Scenario Outline: POST - quote_by_product - testes positivos - <testDescription>
	Given I use the route "/quote_by_product"
      And I use the json body
      """
       {
		  "origin_zip_code": "<origin_zip_code>",
		  "destination_zip_code": "<destination_zip_code>",
		  "quoting_mode": "DYNAMIC_BOX_ALL_ITEMS",
		  "products": [
		    {
		      "weight": 5,
		      "cost_of_goods": 10.7,
		      "width": 15,
		      "height": 17.5,
		      "length": 15,
		      "quantity": 1,
		      "sku_id": "SKU123",
		      "product_category": "Bebidas"
		    },
		    {
		      "weight": 7,
		      "cost_of_goods": 20.99,
		      "width": 20.5,
		      "height": 30.7,
		      "length": 20,
		      "quantity": 1,
		      "sku_id": "<sku_id>",
		      "product_category": "Bebidas"
		    }
		  ],
		  "additional_information": {
		    "lead_time_business_days": 1,
		    "sales_channel": "<sales_channel>",
		    "client_type": "gold"
		  },
		  "identification": {
		    "session": "04e5bdf7ed15e571c0265c18333b6fdf1434658753",
		    "ip": "000.000.000.000",
		    "page_name": "carrinho",
		    "url": "http://www.intelipost.com.br/checkout/cart/"
		  }
		}
      """
    When I send the POST request
    Then Http response should be <responseCode>
     And the response JSON must have "/status" as "<status>"
     And The response JSON list node "/content/delivery_options" must contain all keys "delivery_estimate_business_days" as "20"

    Examples:
    	| testDescription          | origin_zip_code | destination_zip_code | sku_id | sales_channel | responseCode | status |
    	| Origem TO e destino Pará | 77890000        | 66085-635            | SKU456 | CN200         | 200          | OK     |
    	| CEP Pará - 20 dias       | 04012080        | 66010000             | SKU123 | CN200         | 200          | OK     |




  Scenario Outline: POST - quote_by_product - negativos <testDescription>
	Given I use the route "/quote_by_product"
      And I use the json body
      """
       {
		  "origin_zip_code": "<origin_zip_code>",
		  "destination_zip_code": "<destination_zip_code>",
		  "quoting_mode": "DYNAMIC_BOX_ALL_ITEMS",
		  "products": [
		    {
		      "weight": 5,
		      "cost_of_goods": 10.7,
		      "width": 15,
		      "height": 17.5,
		      "length": 15,
		      "quantity": 1,
		      "sku_id": "SKU123",
		      "product_category": "Bebidas"
		    },
		    {
		      "weight": 7,
		      "cost_of_goods": 20.99,
		      "width": 20.5,
		      "height": 30.7,
		      "length": 20,
		      "quantity": 1,
		      "sku_id": "<sku_id>",
		      "product_category": "Bebidas"
		    }
		  ],
		  "additional_information": {
		    "lead_time_business_days": 1,
		    "sales_channel": "<sales_channel>",
		    "client_type": "gold"
		  },
		  "identification": {
		    "session": "04e5bdf7ed15e571c0265c18333b6fdf1434658753",
		    "ip": "000.000.000.000",
		    "page_name": "carrinho",
		    "url": "http://www.intelipost.com.br/checkout/cart/"
		  }
		}
      """
    When I send the POST request
    Then Http response should be <responseCode>
     And the response JSON must have "/status" as "<status>"
     And the response JSON must have "/messages/0/key" as "<messageKey>"
	
	Examples:
      | testDescription                 | origin_zip_code | destination_zip_code | sku_id | sales_channel | responseCode | status | messageKey                |
      | Canal CN1                       | 04012080        | 04304011             | SKU456 | CN1           | 400          | ERROR  | quote.no.delivery.options |
      | Canal CN2                       | 04012080        | 04304011             | SKU456 | CN2           | 400          | ERROR  | quote.no.delivery.options |
      | CEP Pará - Canal CN1            | 04012080        | 66010000             | SKU123 | CN1           | 400          | ERROR  | quote.no.delivery.options |
      | Origem TO e destino interior SP | 77890000        | 18530000             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Origem TO e destino  SP         | 77890000        | 04013003             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Palmas (TO) - RJ interior       | 77001026        | 28999098             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Interior tocantinense - RJ      | 77300000        | 23799899             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Palmas (TO) - MG interior       | 77001026        | 35001970             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Interior tocantinense - MG      | 77300000        | 31873155             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Palmas (TO) - ES interior       | 77001026        | 29100000             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      | Interior tocantinense - ES      | 77300000        | 29092150             | SKU456 | CN200         | 400          | ERROR  | quote.no.delivery.options |
      