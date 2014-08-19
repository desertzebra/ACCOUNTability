<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/html/accountability/init.jsp" %>

<%
PortletURL listTransaction= renderResponse.createActionURL(); 
listTransaction.setParameter(ActionRequest.ACTION_NAME, "insertTransaction");
%>

<table>
<tr>
<td><div id="<portlet:namespace />drop-down-2"><label for="drop-down-2">Donor</label></div>
</td>
<td><div id="<portlet:namespace />drop-down-3"><label for="drop-down-3">Receiver</label></div>
</td>
</tr>
</table>



<aui:script use="aui-autocomplete">
 var dataSource = new A.DataSource.IO(
        {
            source: '<portlet:resourceURL />'
        }
    );
    var autocomplete = new A.AutoComplete(
        {
            dataSource: dataSource,
            //delimChar: ',',
            contentBox: '#<portlet:namespace />drop-down-2',
            matchKey: 'name',
            schema: {
               resultListLocator: 'response',
               resultFields:['entityid', 'name']
      
            },
            schemaType:'json',
            typeAhead: true,
            on:{
           itemSelect:function(event){
           
           if (event !== null && event.details !== null && event.details[1] != null && event.details[1].entityid != null)
            {
				document.getElementById("<portlet:namespace />donorid").value = event.details[1].entityid;
			}
         
           }           
          }
        }
    );
  
    autocomplete.generateRequest = function(query) {
        return {
            request: '&q=' + query
        };
    }
    autocomplete.render();
</aui:script>

<aui:script use="aui-autocomplete">
 var dataSource = new A.DataSource.IO(
        {
            source: '<portlet:resourceURL />'
        }
    );
    var autocomplete = new A.AutoComplete(
        {
            dataSource: dataSource,
            //delimChar: ',',
            contentBox: '#<portlet:namespace />drop-down-3',
            matchKey: 'name',
            schema: {
               resultListLocator: 'response',
               resultFields:['entityid', 'name']
      
            },
            schemaType:'json',
            typeAhead: true,
            on:{
           itemSelect:function(event){
           
           if (event !== null && event.details !== null && event.details[1] != null && event.details[1].entityid != null)
            {
	document.getElementById("<portlet:namespace />receiverid").value = event.details[1].entityid;
				
			}
         
           }           
          }
        }
    );
  
    autocomplete.generateRequest = function(query) {
        return {
            request: '&q=' + query
        };
    }
    autocomplete.render();
</aui:script>
<form id = "transaction" name="<portlet:namespace/>fm" method="POST" action="<%= listTransaction.toString() %>">
<aui:input  name="amount" label="Amount" value=""/> <br>
<aui:input  name="donorid"     type="hidden" value=""/>
<aui:input  name="receiverid" type="hidden"  value=""/>
<aui:button name="buttonInsert" type="submit" value="Insert"/>

</form>