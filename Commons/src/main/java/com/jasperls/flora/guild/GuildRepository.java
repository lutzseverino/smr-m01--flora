package com.jasperls.flora.guild;

import com.jasperls.flora.docs.impl.Guild;

import java.util.concurrent.ExecutionException;

public interface GuildRepository {

    Guild get(String id) throws ExecutionException;
}
