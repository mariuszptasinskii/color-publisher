package mptasinski.co.brick.task.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import mptasinski.co.brick.task.api.model.ColorRequest;
import mptasinski.co.brick.task.api.model.ColorResponse;
import mptasinski.co.brick.task.service.ColorService;

import javax.inject.Inject;

@Controller
public class ColorsController {

    @Inject
    private ColorService colorService;

    @Post(value = "/publish")
    public HttpResponse<ColorResponse> publish(@Body ColorRequest colorRequest) {
        colorService.processColors(colorRequest.getColors());

        return HttpResponse.ok(new ColorResponse());

    }
}
