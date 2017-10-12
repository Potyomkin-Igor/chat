INSERT INTO thymeleaf.roles(id,name)VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

INSERT INTO thymeleaf.users(id, first_name, last_name, password, email)VALUES (1, 'Vasy', 'Petrov', '$2a$10$sAU2RDAdcKzJu08/IebhB.r4Vk6HFNfR8w4.GsgeX.KXgWd0pfnre', 'vasy@vasy.ru'), (2, 'Egor', 'Egorov', '$2a$10$AGXQ.utpekhj2bqtPLtyVev73Hn3MzwFEYNMyFYTKlvaLnf1uxiQy', 'egor@egor.ru'), (3, 'Anton', 'Antonov', '$2a$10$AGXQ.utpekhj2bqtPLtyVev73Hn3MzwFEYNMyFYTKlvaLnf1uxiQy', 'anton@anton.ru'), (4, 'Igor', 'Privalov', '$2a$10$AGXQ.utpekhj2bqtPLtyVev73Hn3MzwFEYNMyFYTKlvaLnf1uxiQy', 'Original2409@gmail.com');

INSERT INTO thymeleaf.users_roles(user_id, role_id)VALUES (1,2),(2,2),(3,2),(4,1);

