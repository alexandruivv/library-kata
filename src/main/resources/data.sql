INSERT INTO users_credentials (id, username, password, user_role) VALUES
                                                          (CAST(X'f7f1f971a1874944ac41f433f0faf2bd' AS BINARY(16)), 'ADMIN', 'ADMIN', 'ADMIN'),
                                                          (CAST(X'5f4e4fd970584d8d999910bcc696144f' AS BINARY(16)), 'user1', 'user1', 'USER'),
                                                          (CAST(X'e2fa38e31662433abc856c534838a4af' AS BINARY(16)), 'user2', 'user2', 'USER');

INSERT INTO users (id, first_name, last_name, user_credentials_id) VALUES
                                                                      (CAST(X'4ca8ffa665c9499d9f8f659f1d67f37d' AS BINARY(16)), 'John', 'Doe', CAST(X'f7f1f971a1874944ac41f433f0faf2bd' AS BINARY(16))),
                                                                      (CAST(X'917386078b22473094061fbb9b57e0c5' AS BINARY(16)), 'Jane', 'Doe', CAST(X'5f4e4fd970584d8d999910bcc696144f' AS BINARY(16))),
                                                                      (CAST(X'3cadae7e55ea40aa972dd2e40dfa9389' AS BINARY(16)), 'Bob', 'Smith', CAST(X'e2fa38e31662433abc856c534838a4af' AS BINARY(16)));

INSERT INTO books (id, title, description, author, publication_date) VALUES
                                                                    (CAST(X'6cfa306231d744cd8189aaca4cd39b1a' AS BINARY(16)), 'To Kill a Mockingbird', 'A novel about moral growth and racial injustice.', 'Harper Lee', '19600711'),
                                                                    (CAST(X'8645f0e3f9be49e78d519fc1b947b5f7' AS BINARY(16)), '1984', 'A dystopian novel about totalitarianism and surveillance.', 'George Orwell', '19490608'),
                                                                    (CAST(X'53ae12945b7e49f494f78e0709adf2e6' AS BINARY(16)), 'The Great Gatsby', 'A critique of the American Dream set in the Roaring Twenties.', 'F. Scott Fitzgerald', '19250410'),
                                                                    (CAST(X'c1ba2d7429c24ccfb99c90fa68f14d41' AS BINARY(16)), 'Moby Dick', 'An epic tale of the sea and the pursuit of the white whale.', 'Herman Melville', '18511018');
