package org.lpny.gr.builder
//-----
def component = builder.component{       
}
assert component != null;
//-----
component = builder.component{
    current.servers.add(protocol.HTTP)
}
assert component != null;
assert component.getServers().size()==1
//-----
component = builder.component{
    application(uri:"")
}
assert component!=null
assert component.defaultHost.routes.size()==1
//----

component = builder.component(){
    application(uri:""){
        def result 
        def r = restlet(handle: {req, resp-> result = "GOOD"})        
        r.handle(null,null)
        assert "GOOD".equals(result) 
    }
}