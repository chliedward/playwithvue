<html>
<body>
	<!-- This is static webpage directly accessed by user. -->
    <h2>Hello World</h2>
    <h3><a href="dispatcher1/helloWorld/hello?name=Sally">Redirect without check (dispatcher1/helloWorld/hello?name=Sally)</a></h3>
    <h3><a href="dispatcher1/hello2?name=Sally">Redirect to another controller's url</a></h3>
    <h3><a href="dispatcher1/helloWorld/helloAuth?name=Sally">Redirect with Auth check (dispatcher1/helloWorld/helloAuth?name=Sally)</a></h3>



    <h2>Other Links for Environment Management</h2>
    <h3><a target="_blank" href="http://localhost:8085/login?from=">Jenkins Manager</a></h3>
    <h3><a target="_blank" href="http://localhost:3010/apex/f?p=4950:1:2054261275740215">Oracle Manager</a></h3>

    <form  class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="userName" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="passwordPlain" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"
                    formmethod="POST"
                    formaction="/my-first-mvn-web-app-front/auth/user/login">Log In</button>
            <button class="btn btn-lg btn-primary btn-block" type="submit"
                    formmethod="POST"
                    formaction="/my-first-mvn-web-app-front/auth/user/registration">Register</button>

            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>

    </form>
</body>
</html>