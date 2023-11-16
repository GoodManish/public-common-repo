
My_Object object2= org.apache.commons.lang.SerializationUtils.clone(object1);


For complicated objects and when performance is not significant i use a json library, like gson to serialize the object to json text, then deserialize the text to get new object.
gson which based on reflection will works in most cases, except that transient fields will not be copied and objects with circular reference with cause StackOverflowError.

    public static <T> T copy(T anObject, Class<T> classInfo) {
        Gson gson = new GsonBuilder().create();
        String text = gson.toJson(anObject);
        T newObject = gson.fromJson(text, classInfo);
        return newObject;
    }
    public static void main(String[] args) {
        String originalObject = "hello";
        String copiedObject = copy(originalObject, String.class);
    }


    package com.example.demo;

    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;

    import java.time.Instant;
    import java.util.List;

    public class TestGsonWayClass {

    public static Bookmark copy(Bookmark anObject, Class<Bookmark> classInfo) {
        Gson gson = new GsonBuilder().create();
        String text = gson.toJson(anObject);
        return gson.fromJson(text, classInfo);
    }
    public static void main(String[] args) {
        TestGsonWayClass test = new TestGsonWayClass();
        Bookmark bookmark = test.create();
        Bookmark clonedObject = copy(bookmark, Bookmark.class);
        System.out.println("bookmark ---\n"+bookmark);
        System.out.println();
        System.out.println("cloneBookmarkObj +++++++\n"+clonedObject);
    }

    public Bookmark create(){
        Address address = new Address();
        address.setCity("city");
        address.setState("USA");
        address.setZip("12345");
        address.setStreet("street1");

        Address address1 = new Address();
        address1.setCity("city");
        address1.setState("INDIA");
        address1.setZip("12345");
        address1.setStreet("street1");

        Bookmark bookmark = new Bookmark();
        bookmark.setId(1L);
        bookmark.setTitle("test1");
        bookmark.setUrl("http://example.com");
        bookmark.setCreatedAt(Instant.now());
        bookmark.setAddresses(List.of(address, address1));

        return bookmark;
    }
    }

        package com.example.demo;
        import lombok.*;
        import java.io.Serializable;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        @ToString
        public class Address /*implements Serializable*/ {
            private String street;
            private String city;
            private String state;
            private String zip;
        }


        package com.example.demo;
        import lombok.*;
        import java.io.Serializable;
        import java.time.Instant;
        import java.util.ArrayList;
        import java.util.List;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public class Bookmark /*implements Serializable*/ {

            private Long id;
            private String title;
            private String url;
            private Instant createdAt;
            private List<Address> addresses = new ArrayList();

        }
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
