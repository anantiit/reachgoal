package com.reachgoal.assignments.myntra.controller;

/**
 * 
 * @author appalanaiduabotula Repair Service Booking Problem Definition A
 *         company wants to provide one stop service point for all electronics,
 *         electrical and IT products. Customers can book services for
 *         electronic devices by providing their device type and preferred time
 *         slot in which they want service. There is one service which takes a
 *         list of requests from the clients and assigns it to eligible service
 *         executives. You are supposed to implement another service which
 *         checks for conflicting slots of executives and resolves them.
 *         Following criteria will be used to resolve conflicts - 1. The system
 *         has a way of rating customers on a scale of 1-5. So, a customer with
 *         a higher rating will be preferred over a customer of lower rating.
 *         For equal ratings, any of them can be selected. 2. The company has
 *         defined an order of product types based on their service cost.
 *         Servicing products of lower rank will be preferred over higher rank.
 *         If the products are of the same rank, anyone can be selected. 3. The
 *         time slot which has an earlier start time should be selected. 4.
 *         Since the company is new, it has only one office location. Customers
 *         closer to that office location will be given more preference over the
 *         farther ones. 5. If all the above parameters are the same, any of the
 *         slots can be selected. Given a slot booking of one executive, find
 *         the slots he should service by removing the conflicting slots. Sample
 *         Input: Customer Ratings - Nitish,4.3 Amit,5.0 John,4.9 Kanhaiya,3.5
 *         Iravati,4.9 Product Order - MOBILE,3 TV,5 AC,4 REFRIGERATOR,1
 *         WASHING_MACHINE,4 PROJECTOR,5 Executive Slot Requests -
 *         Nitish,MOBILE,10:15AM,12:00PM,8,37
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78
 *         Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         John,PROJECTOR,05:30PM,06:00PM,-13,78 Format for each slot <Client
 *         name>,<Product Type>,<Start Time>,<End Time>,<Location X>,<Location
 *         Y> Expected Output - Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78 Guidelines: ● Create the sample data
 *         yourself. You can put it into a file, test case or main driver
 *         program itself. Feel free to decide the format of the input/data
 *         (e.g. CSV, XML, your own format etc.) ● UI is not required, you can
 *         use the console (or file or hard-code) for input and output. ● Please
 *         do not access the internet for anything EXCEPT syntax. ● You are free
 *         to use the language of your choice. ● All work should be your own. If
 *         found otherwise, you may be disqualified. ● You are free to use any
 *         CSV/XML parser library of your choice. You are free to use any file
 *         reading library (if you are taking input from file, that is). ● Do
 *         not spend much time on I/O and data formats; focus on core logic of
 *         the question. Expectations: ● Code should be demo-able (very
 *         important) ● Complete coding within the duration of 90 minutes. ●
 *         Code should be modular, with Object Oriented design. Maintain good
 *         separation of concerns. ● Code should be extensible. It should be
 *         easy to add/remove functionality without rewriting the entire
 *         codebase. ● Code should handle edge cases properly and fail
 *         gracefully. ● Code should be readable. Follow good coding practices -
 *         Use intuitive variable names, function names, class names etc. -
 *         Indent code properly. - Follow best practices of the language you
 *         use.
 * 
 *         Repair Service Booking Problem Definition A company wants to provide
 *         one stop service point for all electronics, electrical and IT
 *         products. Customers can book services for electronic devices by
 *         providing their device type and preferred time slot in which they
 *         want service. There is one service which takes a list of requests
 *         from the clients and assigns it to eligible service executives. You
 *         are supposed to implement another service which checks for
 *         conflicting slots of executives and resolves them. Following criteria
 *         will be used to resolve conflicts - 1. The system has a way of rating
 *         customers on a scale of 1-5. So, a customer with a higher rating will
 *         be preferred over a customer of lower rating. For equal ratings, any
 *         of them can be selected. 2. The company has defined an order of
 *         product types based on their service cost. Servicing products of
 *         lower rank will be preferred over higher rank. If the products are of
 *         the same rank, anyone can be selected. 3. The time slot which has an
 *         earlier start time should be selected. 4. Since the company is new,
 *         it has only one office location. Customers closer to that office
 *         location will be given more preference over the farther ones. 5. If
 *         all the above parameters are the same, any of the slots can be
 *         selected. Given a slot booking of one executive, find the slots he
 *         should service by removing the conflicting slots. Sample Input:
 *         Customer Ratings - Nitish,4.3 Amit,5.0 John,4.9 Kanhaiya,3.5
 *         Iravati,4.9 Product Order - MOBILE,3 TV,5 AC,4 REFRIGERATOR,1
 *         WASHING_MACHINE,4 PROJECTOR,5 Executive Slot Requests -
 *         Nitish,MOBILE,10:15AM,12:00PM,8,37
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78
 *         Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         John,PROJECTOR,05:30PM,06:00PM,-13,78 Format for each slot <Client
 *         name>,<Product Type>,<Start Time>,<End Time>,<Location X>,<Location
 *         Y> Expected Output - Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78 Guidelines: ● Create the sample data
 *         yourself. You can put it into a file, test case or main driver
 *         program itself. Feel free to decide the format of the input/data
 *         (e.g. CSV, XML, your own format etc.) ● UI is not required, you can
 *         use the console (or file or hard-code) for input and output. ● Please
 *         do not access the internet for anything EXCEPT syntax. ● You are free
 *         to use the language of your choice. ● All work should be your own. If
 *         found otherwise, you may be disqualified. ● You are free to use any
 *         CSV/XML parser library of your choice. You are free to use any file
 *         reading library (if you are taking input from file, that is). ● Do
 *         not spend much time on I/O and data formats; focus on core logic of
 *         the question. Expectations: ● Code should be demo-able (very
 *         important) ● Complete coding within the duration of 90 minutes. ●
 *         Code should be modular, with Object Oriented design. Maintain good
 *         separation of concerns. ● Code should be extensible. It should be
 *         easy to add/remove functionality without rewriting the entire
 *         codebase. ● Code should handle edge cases properly and fail
 *         gracefully. ● Code should be readable. Follow good coding practices -
 *         Use intuitive variable names, function names, class names etc. -
 *         Indent code properly. - Follow best practices of the language you
 *         use.
 * 
 *         Repair Service Booking Problem Definition A company wants to provide
 *         one stop service point for all electronics, electrical and IT
 *         products. Customers can book services for electronic devices by
 *         providing their device type and preferred time slot in which they
 *         want service. There is one service which takes a list of requests
 *         from the clients and assigns it to eligible service executives. You
 *         are supposed to implement another service which checks for
 *         conflicting slots of executives and resolves them. Following criteria
 *         will be used to resolve conflicts - 1. The system has a way of rating
 *         customers on a scale of 1-5. So, a customer with a higher rating will
 *         be preferred over a customer of lower rating. For equal ratings, any
 *         of them can be selected. 2. The company has defined an order of
 *         product types based on their service cost. Servicing products of
 *         lower rank will be preferred over higher rank. If the products are of
 *         the same rank, anyone can be selected. 3. The time slot which has an
 *         earlier start time should be selected. 4. Since the company is new,
 *         it has only one office location. Customers closer to that office
 *         location will be given more preference over the farther ones. 5. If
 *         all the above parameters are the same, any of the slots can be
 *         selected. Given a slot booking of one executive, find the slots he
 *         should service by removing the conflicting slots. Sample Input:
 *         Customer Ratings - Nitish,4.3 Amit,5.0 John,4.9 Kanhaiya,3.5
 *         Iravati,4.9 Product Order - MOBILE,3 TV,5 AC,4 REFRIGERATOR,1
 *         WASHING_MACHINE,4 PROJECTOR,5 Executive Slot Requests -
 *         Nitish,MOBILE,10:15AM,12:00PM,8,37
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78
 *         Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         John,PROJECTOR,05:30PM,06:00PM,-13,78 Format for each slot <Client
 *         name>,<Product Type>,<Start Time>,<End Time>,<Location X>,<Location
 *         Y> Expected Output - Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 *         Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97
 *         Iravati,TV,05:00PM,06:00PM,13,78 Guidelines: ● Create the sample data
 *         yourself. You can put it into a file, test case or main driver
 *         program itself. Feel free to decide the format of the input/data
 *         (e.g. CSV, XML, your own format etc.) ● UI is not required, you can
 *         use the console (or file or hard-code) for input and output. ● Please
 *         do not access the internet for anything EXCEPT syntax. ● You are free
 *         to use the language of your choice. ● All work should be your own. If
 *         found otherwise, you may be disqualified. ● You are free to use any
 *         CSV/XML parser library of your choice. You are free to use any file
 *         reading library (if you are taking input from file, that is). ● Do
 *         not spend much time on I/O and data formats; focus on core logic of
 *         the question. Expectations: ● Code should be demo-able (very
 *         important) ● Complete coding within the duration of 90 minutes. ●
 *         Code should be modular, with Object Oriented design. Maintain good
 *         separation of concerns. ● Code should be extensible. It should be
 *         easy to add/remove functionality without rewriting the entire
 *         codebase. ● Code should handle edge cases properly and fail
 *         gracefully. ● Code should be readable. Follow good coding practices -
 *         Use intuitive variable names, function names, class names etc. -
 *         Indent code properly. - Follow best practices of the language you
 *         use.
 * 
 * 
 */
public class ConflictingSlotResolver {
	public static void main(String[] args) {

	}
}
