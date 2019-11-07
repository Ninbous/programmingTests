public class Yandex implements ISearchable{

    public String getBaseUrl() {
        //Основной урл
        String baseUrl = "https://yandex.ru/search/?lr=43&text=";
        return baseUrl;
    }

    public String getSearchResultLinks() {
        //Ссылки результатов
        String aSelector = ".serp-item h2 a";
        return aSelector;
    }

}
