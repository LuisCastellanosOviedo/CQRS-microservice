package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoderImpl;
import com.springbank.user.core.events.UserRegisteredEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;
    private final PasswordEncoder passwordEncoder;

    public UserAggregate() {
        passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command) {
        User user = command.getUser();
        user.setId(command.getId());
        String password = user.getAccount().getPassword();
        passwordEncoder = new PasswordEncoderImpl();
        String hashedPassword = passwordEncoder.hashPasseord(password);
        user.getAccount().setPassword(hashedPassword);

        UserRegisteredEvent event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(user)
                .build();

        AggregateLifecycle.apply(event);

    }

    @CommandHandler
    public void handler(UpdateUserCommand command){
        User userUpdated = command.getUser();
        userUpdated.setId(command.getId());
        String password = userUpdated.getAccount().getPassword();
        String hashedPassword = passwordEncoder.hashPasseord(password);
        userUpdated.getAccount().setPassword(hashedPassword);

        UserUpdatedEvent event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(userUpdated)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handler(RemoveUserCommand command){
        UserRemovedEvent event = new UserRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event){
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event){
        AggregateLifecycle.markDeleted();
    }


}
