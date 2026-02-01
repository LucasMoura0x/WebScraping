# WebScraping

Web Scraper & Excel Generator
Este sistema automatiza a busca de produtos na Amazon Brasil, extrai informações críticas e exporta tudo para uma planilha Excel formatada.

Tecnologias e Arquitetura
O sistema segue o padrão MVC (Model-View-Controller) simplificado:

Model (Produtos.java): Responsável pelo armazenamento e pela inteligência de extração de dados via RegEx e manipulação de BigDecimal.

Controller (WebScraping.java): Gerencia o ciclo de vida do Selenium (coleta) e a escrita de dados via Apache POI (geração do Excel).

Resolução de Problemas (Troubleshooting)
Se o sistema não se comportar como esperado, verifique os pontos abaixo:

1. O arquivo Excel não é gerado ou a mensagem não aparece
Arquivo em uso: Se o produtos.xlsx estiver aberto no seu Excel, o Java receberá um erro de "Acesso Negado". Feche o Excel e execute novamente.

Bloqueio de Anti-Bot: A Amazon pode identificar o acesso automatizado. No código, tente comentar a linha options.addArguments("--headless=new"); para rodar com o navegador visível. Isso muitas vezes resolve problemas de carregamento de elementos.

2. Erro de versão do WebDriver
Versão do Edge: O erro Unable to find version of CDP ou falha ao iniciar o driver geralmente significa que seu Edge atualizou e o seu msedgedriver.exe ficou antigo.

Solução: Baixe a versão correspondente em Microsoft Edge Driver e substitua na pasta resources/.

3. Valores aparecendo como "0" ou "Vazio" na planilha
Mudança no Layout do Site: Web Scraping depende do HTML do site. Se a Amazon mudar as classes das tags (como a-price), os seletores XPath no código precisarão ser atualizados.

Lógica de Strings: Verifique se o formato do preço no site mudou (ex: de "R$ 1.000,00" para "1000.00"). A lógica no método getValorAvista(String) pode precisar de ajustes no replaceAll.


Sugestões de Melhorias Futuras
Paginação: Implementar um loop para navegar pelas páginas 2, 3, 4 da pesquisa.

Banco de Dados: Em vez de apenas Excel, salvar os dados em um banco SQL para histórico de preços.

Envio de E-mail: Notificar o usuário automaticamente quando um smartphone atingir um preço alvo.
