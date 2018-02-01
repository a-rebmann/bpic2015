import csv
import pprint
import time
import datetime

file_path = "C:\\Users\\Matthias\\Dropbox\\PM Fallstudie\\Datasets\\CSV\\BPIC15_"

def csv_dict_list(file_path):
    reader = csv.DictReader(open(file_path, 'rt'))
    dict_list = []
    for line in reader:
        dict_list.append(line)
    return dict_list

#Computes the Resource-Actvity-Matrix of a given Log
def resouceActivityMatrix(log_number):
    matrix = dict()
    activities = []
    trace_list = csv_dict_list(file_path + str(log_number)+ ".csv")
    for t in trace_list:
        if t["activityNameEN"] not in activities:
            activities.append(t["activityNameEN"])

        if matrix.get(t["Resource"]) is not None:
            if matrix[t["Resource"]].get(t["activityNameEN"]) is not None:
                matrix[t["Resource"]][t["activityNameEN"]] = matrix[t["Resource"]][t["activityNameEN"]] + 1
            else:
                matrix[t["Resource"]][t["activityNameEN"]]= 1
        else:
            d = {t["activityNameEN"] : 1}
            matrix[t["Resource"]] = d

    #Add empty values, Divide the Number by ??? -> write into csv afterwards
    with open("C:\\Users\\Matthias\\Desktop\\Resource_Activity.csv", 'w',newline='') as analyse:
        writer = csv.writer(analyse, delimiter=',', quoting=csv.QUOTE_MINIMAL)
        writer.writerow(["Resources"]+sorted(activities))
        for resource in matrix:
            for activity in activities:
                if matrix[resource].get(activity) is None:
                    matrix[resource][activity] = 0
                else:
                    matrix[resource][activity] = matrix[resource][activity] #/ 1    fill in suitable number!
            sorted(matrix[resource])

            writer.writerow()

resouceActivityMatrix(1)

# Ermittelt Kombination aus Monitoring, Resources und Responsible
def getCombinationResources():
    resources = set()
    res_mon = set()
    res_actors = set()
    with open("C:\\Users\\Matthias\\Desktop\\Analyse.csv", 'w',newline='') as analyse:
        wr_1 = csv.writer(analyse, delimiter=',', quoting=csv.QUOTE_MINIMAL)
        for i in range(1,6):
            trace_list = csv_dict_list(file_path + str(i)+ ".csv")
            matches = []
            resources_part= set()
            res_mon_part= set()
            res_actors_part= set()
            for t in trace_list:
                resources.add(t["Resource"])
                res_mon.add(t["monitoringResource"])
                res_actors.add(t["(case) Responsible_actor"])
                resources_part.add(t["Resource"])
                res_mon_part .add(t["monitoringResource"])
                res_actors_part.add(t["(case) Responsible_actor"])
                insert = False
                for m in matches:
                    if m["Monitoring"] == t["monitoringResource"] and m["Resource"] == t["Resource"] and m["Responsible"] == t["(case) Responsible_actor"]:
                        m["count"] = m["count"] + 1
                        insert = True
                if not insert:
                    d = {"Monitoring" : t["monitoringResource"], "Resource" : t["Resource"], "Responsible" : t["(case) Responsible_actor"], "count" : 1}
                    matches.append(d)
            print("Log" + str(i))
            print("Resourcen ", len(resources_part)," | " "Monitoring", len(res_mon_part)," | ", "Responsible", len(res_actors_part))
            print("Überschneidung Resource und Monitoring ", len(resources_part.intersection(res_mon_part)))
            print("Überschneidung Resource und Responsible ", len(resources_part.intersection(res_actors_part)))
            print("Überschneidung Monitoring und Responsible ", len(res_mon_part.intersection(res_actors_part)))
            print("Überschneidung von allen", len((res_mon_part.intersection(res_actors_part)).intersection(resources_part)))


            with open("C:\\Users\\Matthias\\Desktop\\test"+str(i)+".csv", 'w',newline='') as myfile:
                wr = csv.writer(myfile, delimiter=',', quoting=csv.QUOTE_MINIMAL)
                #write header
                wr.writerow(["Monitoring", "Resource", "Responsible", "Anzahl"])
                #sorted(matches, key = getKey)
                for m in matches:
                    wr.writerow([m["Monitoring"],m["Resource"], m["Responsible"], m["count"]])
        print("Allgemein")
        print("Resourcen ", len(resources)," | ", "Monitoring", len(res_mon)," | ", "Responsible", len(res_actors))
        print("Überschneidung Resource und Monitoring ", len(resources.intersection(res_mon)))
        print("Überschneidung Resource und Responsible ", len(resources.intersection(res_actors)))
        print("Überschneidung Monitoring und Responsible ", len(res_mon.intersection(res_actors)))
        print("Überschneidung von allen", len((res_mon.intersection(res_actors)).intersection(resources)))

        wr_1.writerow(["All3"])
        all = res_mon.intersection(res_actors).intersection(resources)
        wr_1.writerow(all)
        wr_1.writerow(["Überschneidung Resource und Monitoring - All"])
        r_m = (resources.intersection(res_mon)).difference(all)
        wr_1.writerow(r_m)
        print(len(r_m))
        wr_1.writerow(["Überschneidung Resource und Responsible - ALL"])
        r_r = (resources.intersection(res_actors)).difference(all)
        wr_1.writerow(r_r)
        print(len(r_r))
        wr_1.writerow(["Überschneidung Monitoring und Responsible - ALL"])
        m_r = (res_mon.intersection(res_actors)).difference(all)
        wr_1.writerow(m_r)
        print(len(m_r))
        wr_1.writerow(["only Resources"])
        wr_1.writerow(resources.difference(all).difference(r_m).difference(r_r))
        wr_1.writerow(["only Monitoring"])
        wr_1.writerow(res_mon.difference(all).difference(r_m).difference(m_r))
        wr_1.writerow(["only Responsible"])
        wr_1.writerow(res_actors.difference(all).difference(m_r).difference(r_r))

#getCombinationResources()

def mitarbeiter_wechsel():
    #Set Up Resource Sets
    resource_sets = dict()
    for i in range(1, 6):
        trace = csv_dict_list(file_path + str(i)+ ".csv")
        resources = set()
        for t in trace:
            resources.add(t["Resource"])
        resource_sets[str(i)] = resources


    for i in range(1, 6):
        print("------------------- Log", i, " -------------------")
        overlaps = []
        trace_list = csv_dict_list(file_path + str(i)+ ".csv")
        for t in trace_list:
            res = t["Resource"]
            for x in range(1, 6):
                if x != i:
                    for resource in resource_sets[str(x)]:
                        if res == resource:
                            #print (res, x, t["Complete Timestamp"])

                            if not overlaps:
                                overlap = {"R" : res, "Log" : str(x), "start" : t["Complete Timestamp"], "end" : t["Complete Timestamp"], "sum" : 1}
                                overlaps.append(overlap)

                            else:
                                insert = False
                                for o in overlaps:
                                    if o["R"] == res and str(x) == o["Log"]:
                                        start = time.mktime(datetime.datetime.strptime((o["start"])[:-13], "%Y/%m/%d").timetuple())
                                        end = time.mktime(datetime.datetime.strptime((o["end"])[:-13], "%Y/%m/%d").timetuple())
                                        akt = time.mktime(datetime.datetime.strptime(t["Complete Timestamp"][:-13], "%Y/%m/%d").timetuple())

                                        if akt < start:
                                            o["start"] = t["Complete Timestamp"]
                                        if akt > end:
                                            o["end"] = t["Complete Timestamp"]
                                        o["sum"] = o["sum"] + 1
                                        insert = True
                                if not insert:
                                    overlap = {"R" : res, "Log" : str(x), "start" : t["Complete Timestamp"], "end" : t["Complete Timestamp"], "sum" : 1}
                                    overlaps.append(overlap)
        pprint.pprint (overlaps)