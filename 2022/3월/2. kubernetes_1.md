## 실행 환경

play with kubernetes

```java
**master node에 대해서**
1. Initializes cluster master node:

 kubeadm init --apiserver-advertise-address $(hostname -i) --pod-network-cidr 10.5.0.0/16
    

 2. Initialize cluster networking:

kubectl apply -f https://raw.githubusercontent.com/cloudnativelabs/kube-router/master/daemonset/kubeadm-kuberouter.yaml

**worker nodes에 대해서** 
kubeadm join 192.168.0.13:6443 --token 5rbcdy.fx1tcm21uwwvd292 \
    --discovery-token-ca-cert-hash sha256:cd906f3d87ea095c0cb3ff5b516345eae20ae3151df77e6ffbbe2bd90f2c841c
```

### kubectl이란?

![image](https://user-images.githubusercontent.com/47748246/156999464-d97f4b97-dd8a-48ba-8d21-807ba9469e3b.png)

- master 노드에 “쿠버야 나 웹서버 3개 실행해줘”라고 요청하는 것 = kubectl 명령어
- 즉 쿠버네티스에게 내가 원하는 걸 요청할 때 쓰는 명령어가 kubectl 명령어이다.

### kubectl 명령어 기본 구조

<aside>
📖 kubectl [command] [TYPE] [NAME] [flags]

예) kubectl get pod webserver -o wide
: 쿠버네티스야 나 webserver의 pode 정보 좀 자세하게 알려줘

</aside>

- `command` : 자원(object)에 실행할 명령 (create, get, delete, edit ...)
- `TYPE` :  자원의 타입 (node, pod, service)
- `NAME` : 자원의 이름
- `flag` :  부가적으로 설정할 옵션 (—help, -o options)

### kubectl 명령어들

- kubectl —help
- kubectl command —help

- kubectl run <자원이름> <옵션>
- kubectl create -f obj.yaml
- kubectl apply -f obj.yaml

- kubectl get <자원이름> <객체이름>
- kubectl edit <자원이름> <객체이름>
- kubectl describe <자원이름> <객체이름>

- kubectl delete pod main

### kubectl 명령을 이용해서 컨테이너 실행해 보기

```java
kubectl run webserver --image=nginx:1.14 --port 80
```

- run : 컨테이너 pod를 1개 만드는 명령
- webserver : 컨테이너 이름을 webserver로 할 것임
- --image=nginx:1.14   : nginx1.14 버전 이미지를 가져와서
- --port 80  : 80 포트로 실행 시킬 것임

확인 : 실행 중인 pods 정보 보기

```java
[node1 ~]$ kubectl get pods -o wide
NAME        READY   STATUS    RESTARTS   AGE     IP         NODE    NOMINATED NODE   READINESS GATES
webserver   1/1     Running   0          6m39s   10.5.1.2   node2   <none>           <none>
```

확인2 : 실행 중인 웹서버 접속해보기

```java
curl 10.5.1.2 
```

컨테이너 여러 개 실행 : create deployment

```java
kubectl create deployment mainui --image=httpd --replicas=3
```

다음과 같이 확인

```java
kubectl get deployments.apps

kubectl get pods도 된다. 
```

### 컨테이너로 들어가서 파일 수정하기

- 현재 pods 목록

```java
[node1 ~]$ kubectl get pods -o wide
NAME                     READY   STATUS    RESTARTS   AGE   IP         NODE    NOMINATED NODE   READINESS GATES
mainui-d77bf4d8f-2mscj   1/1     Running   0          14m   10.5.2.3   node3   <none>           <none>
mainui-d77bf4d8f-78xff   1/1     Running   0          14m   10.5.1.3   node2   <none>           <none>
mainui-d77bf4d8f-pr8lb   1/1     Running   0          14m   10.5.2.2   node3   <none>           <none>
webserver                1/1     Running   0          30m   10.5.1.2   node2   <none>           <none>
```

- 이 중에서 webserver의 index.html 파일을 변경하고자 한다.

- webserver 컨테이너에 접속
