package org.example;

import lombok.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class User implements Serializable {

    private Integer id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Integer age;

    @NonNull
    private String role;

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        } else {
            User user = (User) o;

                if((this.lastName.equals(user.lastName))&& (this.firstName.equals(user.firstName))&& (this.age==user.age)&& (this.role.equals(user.role)))
                    return true;
                else
                    return false;

        }
    }
}
