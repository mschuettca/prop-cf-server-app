package com.msapps.property.rental;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.stereotype.Component;

 //   @Component
    public class CustomerLoader implements CommandLineRunner {
        private final CustomerRepository repository;

        @Autowired
        public CustomerLoader(CustomerRepository repository) {
            this.repository = repository;
        }
        @Override
        public void run(String... args) throws Exception {
            System.out.println("Loading Customers...");
            repository.deleteAll();

            // save a couple of customers
            repository.save(new Customer("Alice", "Smith"));
            repository.save(new Customer("Bob", "Smith"));

            this.repository.findAll().forEach(System.out::println);
        }
    }
